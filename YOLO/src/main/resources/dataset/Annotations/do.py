import fileinput

path1 = 'C:\\Users\\Dawid\\Desktop\\windows_v1.8.1\\data\\images\\'
path2 = '/home/carvinus/Desktop/AGH/Maths_projects/YOLO/src/main/resources/dataset/images/'
path3 = '/home/carvinus/Desktop/AGH/Maths_projects/YOLO/src/main/resources/dataset/Annotations/'
x = '.xml'

for i in range(1,121):
	full_path = path3 + str(i) + x
	# Read in the file
	with open(full_path, 'r') as file :
	  filedata = file.read()

	# Replace the target string
	filedata = filedata.replace(path1, path2)

	# Write the file out again
	with open(full_path, 'w') as file:
	  file.write(filedata)
