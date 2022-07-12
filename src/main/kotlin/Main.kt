

fun main(args: Array<String>) {
    parse(args)
    try {
        val template = loadTemplate("templates/c.genert")
        for(i in template.children) {
            if(!i.isFile && (i as NDirectory).children.size != 0) {
                val tmp: NDirectory = i as NDirectory;
                tmp.children
            }
        }
    } catch(e: java.nio.file.FileAlreadyExistsException) {
        System.err.println("Directory ${e.file} already exists")
    }
}

fun parse(args: Array<String>) {
    // TODO
}