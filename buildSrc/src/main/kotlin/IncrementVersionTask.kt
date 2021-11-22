import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class IncreaseVersionTask: DefaultTask() {

    @TaskAction
    fun increaseRevisionNumber() {
        VERSION_NAME_REVISION++
        println("new revision number: $VERSION_NAME_REVISION")
    }
}