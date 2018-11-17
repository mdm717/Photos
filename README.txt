FXMLs are made

Log In is functional for admin and search for other users

Admin Sub System Opens
	serialized users - list can be closed and re-opened

User Sub System Opens
	Loads list of albums for each user
	Add/Edit/Delete work and perform the proper operations on the corresponding files, using the naming convention specified below.
	

Photo class created - contains
	url to photo - set/get
	photo caption - set/get
	date - set/get
	tags - add/delete
	
Albums should be a linked list of photos, with the PhotoNode class
	serialized with format "<user>_<album_name>.dat" in src/album