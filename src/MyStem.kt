import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MyStem(exePath: String, args: String, textPath: String) {

    private val processBuilder: ProcessBuilder = ProcessBuilder(exePath, args, textPath)
    private val process: Process = processBuilder.start()

    fun getAnnotatedText(): String {
        return try {
            val inputStreamReader = InputStreamReader(process.inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            bufferedReader.use { it.readText() }
        } catch (e: IOException) {
            "getAnnotatedText error: ${e.message}"
        }
    }
}