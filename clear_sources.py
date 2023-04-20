import os

clear = []
with open("sources.txt") as f:
    for id, path in enumerate(f):
        a = path.split("\src")[1][:-1]
        clear.append(".\src" + a)

with open("sources.txt", "w") as f:
    for path in clear:
        f.write(path + "\n")