package life.league.challenge.kotlin.util

import java.util.Base64

public object Base64 {
    @JvmStatic
    public fun encodeToString(input: ByteArray?, flags: Int): String {
        return Base64.getEncoder().encodeToString(input)
    }

    @JvmStatic
    public fun decode(str: String?, flags: Int): ByteArray {
        return Base64.getDecoder().decode(str)
    }
}
