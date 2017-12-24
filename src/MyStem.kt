import java.io.IOException
import java.io.BufferedReader
import java.io.InputStreamReader

class MyStem(exePath: String, args: String, textPath: String) {

    private val processBuilder: ProcessBuilder = ProcessBuilder(exePath, args, textPath)
    private val process: Process = processBuilder.start()

    fun getAnnotatedText(): List<String> {
        return try {
            val paragraphs = mutableListOf<String>()
            val inputStreamReader = InputStreamReader(process.inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            bufferedReader.use { it.readLines()}
        } catch (e: IOException) {
            listOf("getAnnotatedText error: ${e.message}")
        }
    }
}