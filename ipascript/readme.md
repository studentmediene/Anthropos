##Usage

```
python ipascript.py [options]

```

######Options

	add-user <file>
		Promts for all necessery information if no file is provided.
		If a file is provided, all users in the file will be added with the information provided in the file.
		Each user in the file has to be an seperate lines, and in the following format:
		<username>;<fist name>;<lastname>;<mail>;<telephoneNumber>;<group>
		The group-option is optional.
	
	add-group "<group name>" "<description>"
		Both <group name> and <description> is mandatory

	del-user <username>
		Deletes the user from the database

	del-group <group name>
		Deletes the group from the database

	add-all <group name>
		Adds all people in the database to the group specified. Use with caution!

	help
		Displays the help-text
