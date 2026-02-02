package michalr.tasks

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<TasksApplication>().with(TestcontainersConfiguration::class).run(*args)
}
