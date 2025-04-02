import android.content.Context

object BlockedNumbers {
    private const val PREF_NAME = "BlockedNumbers"
    private const val KEY_BLOCKED_LIST = "BlockedList"

    fun addNumber(context: Context, number: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val set = prefs.getStringSet(KEY_BLOCKED_LIST, HashSet()) ?: HashSet()
        set.add(number)
        prefs.edit().putStringSet(KEY_BLOCKED_LIST, set).apply()
    }

    fun removeNumber(context: Context, number: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val set = prefs.getStringSet(KEY_BLOCKED_LIST, HashSet()) ?: HashSet()
        set.remove(number)
        prefs.edit().putStringSet(KEY_BLOCKED_LIST, set).apply()
    }

    fun isBlocked(context: Context, number: String): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val set = prefs.getStringSet(KEY_BLOCKED_LIST, HashSet()) ?: HashSet()
        return set.contains(number)
    }

    fun getBlockedNumbers(context: Context): Set<String> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY_BLOCKED_LIST, HashSet()) ?: HashSet()
    }
}
