if [ -f ./text8 ]
then
    mv text* /tmp
fi
if [ -f ./questions-words.txt ]
then
    mv questions-words.txt /tmp
fi
echo '<Train>     You can push to remote'