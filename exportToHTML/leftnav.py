import os
filePaths = list()

def findFiles(dir,type,exclude,array):
    for fname in os.listdir(dir):
        path = os.path.join(dir, fname)
        if os.path.isdir(path):      
            findFiles(path,type,exclude,array)
        if type in path and  len(exclude) > 0:
            if exclude not in path:
                array.append(path);
        elif type in path :
            array.append(path);


findFiles(".",".java.html","",filePaths)

for file in filePaths:
    url = file.replace(file[:2],"")
    print("<a href="+url+">"+url.replace(".html","").replace("/",".")+"</a><br/>")