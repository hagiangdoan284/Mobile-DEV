package com.example.blocknumbers
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.widget.Toast

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (state == TelephonyManager.EXTRA_STATE_RINGING && incomingNumber != null) {
                if (BlockedNumbers.isBlocked(context, incomingNumber)) {
                    endCall(context)
                    Toast.makeText(context, "Cuộc gọi từ $incomingNumber đã bị chặn", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun endCall(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val telecomManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
            telecomManager.endCall()
        } else {
            try {
                val telephony = Class.forName("com.android.internal.telephony.ITelephony")
                val telephonyService = telephony.getDeclaredMethod("getITelephony")
                telephonyService.isAccessible = true
                val telephonyInstance = telephonyService.invoke(null)
                val endCallMethod = telephony.getDeclaredMethod("endCall")
                endCallMethod.invoke(telephonyInstance)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
