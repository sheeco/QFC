cat access-1.log | awk '{print $1}' | sort | uniq -c | sort -nrk 1 | head -10 | awk '{print "IP: "$2"\tAccess Count: "$1}' > access-1.result
cat access-2.log | awk '{print $2}' | sort | uniq -c | sort -nrk 1 | head -10 | awk '{print "IP: "$2"\tAccess Count: "$1}' > access-2.result
