//----------------------------------------------------------------------------//
// Методы использующие vanessa-runner
//----------------------------------------------------------------------------//

// Выполнить проверку используя edt
def edtValidate(ppSettings) {
	def desPath = fileUtils.createChildFilePath(WORKSPACE, "configuration");
	def tmpPath = fileUtils.createChildFilePath(ppSettings.tempDir, "configuration");
	def excludePath = fileUtils.createChildFilePath(WORKSPACE, "exclude-err-edt.txt");
	def junit = fileUtils.createChildFilePath(WORKSPACE, "edt-junit.xml");

	cmd.run("vrunner edt-validate --project-list \"$desPath\" --workspace-location \"$tmpPath\" --exception-file \"$excludePath\" --junitpath \"$junit\"", "Формирование отчета EDT");
}
