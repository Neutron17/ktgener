package com.neutron.ktgener

import org.apache.commons.cli.*
import kotlin.properties.Delegates
import kotlin.system.exitProcess

const val VERSION = 1.1;
const val progName = "boiler plate generator"

class Args {
	lateinit var typeName: String
	lateinit var dirName: String
	lateinit var userName: String
	var isDebug by Delegates.notNull<Boolean>()
}

fun main(args: Array<String>) {
	val arg = parse(args)
	val template = loadTemplate("templates/${arg.typeName}.genert",
		arg.dirName, arg.userName)
	/*for (i in template.children) {
		if (!i.isFile && (i as NDirectory).children.size != 0) {
			val tmp: NDirectory = i
		}
	}*/
}

fun parse(args: Array<String>): Args {
	val ret = Args()
	if ("-V" in args || "--version" in args) {
		println("Version: $VERSION")
		exitProcess(0)
	}
	val options = Options()
	val typeOpt = Option("t","type",true,"-t <arg>: ")
	typeOpt.isRequired = true
	val outOpt = Option("o","output",true,"-o <dir>: output directory")
	val userOpt = Option("u","username",true,"-u <name>: name of the user")
	val debugOpt = Option("d","debug",false,"-d: debug")
	options.addOption(typeOpt)
	options.addOption(outOpt)
	options.addOption(userOpt)
	options.addOption(debugOpt)

	val parser: CommandLineParser = DefaultParser()
	val formatter = HelpFormatter()
	val cmd: CommandLine
	try {
		cmd = parser.parse(options, args)
	} catch (e: ParseException) {
		System.err.println(e.localizedMessage)
		formatter.printHelp(progName, options)
		exitProcess(0)
	}
	if (cmd.hasOption("help")) {
		formatter.printHelp(progName, options)
		exitProcess(0)
	}
	if(!cmd.hasOption("output")) ret.dirName = cmd.getOptionValue("type")
	else ret.dirName = cmd.getOptionValue("output")

	if(cmd.hasOption("username")) ret.userName = cmd.getOptionValue("username")
	else ret.userName = "username"

	if(cmd.hasOption("debug")) ret.isDebug = true
	ret.typeName = cmd.getOptionValue("type")

	return ret;
}