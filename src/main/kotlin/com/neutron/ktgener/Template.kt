package com.neutron.ktgener

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Stack
import kotlin.io.path.Path
import kotlin.io.path.createDirectory

interface IObject {
	var name:String
	val isFile: Boolean
}

class NFile : IObject {
	override var name: String = ""
	override val isFile: Boolean = true
	var content: String = ""
	constructor();
	constructor(name: String) {
		this.name = name
	}
	constructor(name: String, content: String) {
		this.name = name
		this.content = content
	}
}
class NDirectory : IObject {
	override var name: String = ""
	override val isFile: Boolean = false
	val children = mutableListOf<IObject>()
	var parent: NDirectory? = null
	constructor()
	constructor(name: String) {
		this.name = name
	}
	constructor(name: String, parent: NDirectory) {
		this.name = name
		this.parent = parent
	}
}

fun loadTemplate(fileName: String, projectName: String, userName: String): NDirectory {
	val ret = NDirectory()
	val lines = File(fileName).readLines().toMutableList()
	val firstLine = lines[0].lowercase().split(' ')
	assert(firstLine[0] == "template")
	ret.name = projectName.replace("\"", "")
	lines.forEach {
		lines[lines.indexOf(it)] =
			it.replace("#!#PNAME#!#", ret.name)
				.replace(
					"#!#TIME#!#",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
				)
				.replace("#!#UNAME#!#", userName)
	}
	lines.forEach(::println)
	try {
		Path(ret.name).createDirectory()
	} catch (e: java.nio.file.FileAlreadyExistsException) {
		System.err.println("Directory ${e.file} already exists")
	}
	var i = 0
	var file = File("out.txt").bufferedWriter()
	val dirs = Stack<NDirectory>()
	dirs.add(ret)
	while (i < lines.size) {
		val line = lines[i].trim().split(' ').toMutableList()
		//line.forEach { line[line.indexOf(it)] = it.trim() }
		//println(line)
		when (line[0].lowercase()) {
			"tend" -> {
				file.flush()
				file.close()
				return ret
			}
			"dir" -> {
				var path = ""
				dirs.forEach {
					path += it.name + "/"
				}
				println(path);
				try {
					Path(path + line[1].replace("\"", "")).createDirectory()
				} catch (e: java.nio.file.FileAlreadyExistsException) {
					System.err.println("Directory ${e.file} already exists")
				}
				dirs.add(NDirectory(line[1].replace("\"", ""), dirs.peek()))
			}
			"file" -> {
				var path = ""
				dirs.forEach {
					path += it.name + "/"
				}
				println(path)
				val fileName = path + line[1].replace("\"", "")
				file.close()
				file = File(fileName).bufferedWriter()
				var tmp = lines[i + 1]
				var content = ""
				while (tmp.lowercase().trim() != "fend") {
					file.write(tmp + '\n')
					content += tmp + '\n'
					i++
					tmp = lines[i + 1]
				}
				val nFile = NFile(fileName, content)
				dirs.peek().children.add(nFile)
			}
			"dend" -> {
				dirs.pop()
			}
		}
		i++
	}
	file.flush()
	file.close()
	return ret
}