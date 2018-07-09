function douban() {

	list="temp_list.txt"
	result="result.txt"
	log="log.txt"
	# Write to a new file each time
	echo -n "" > "$result"
	echo -n "" > "$log"

	echo -n "Comparing with black list ..."
 	sort id_list.txt -o temp_all.txt; sort id_blacklist.txt -o temp_blacklist.txt
	comm temp_all.txt temp_blacklist.txt -2 -3 > "$list"
	rm temp_all.txt temp_blacklist.txt
 	# cat "test_list.txt" > "$list"
	nline=$(cat $list | wc -l)
	echo $nline" ids are left."

	cat "$list" | awk '{if($0!="")print}' | while read line
	do
		echo -n "Retrieving book "$line" ..."
		echo "[Curl] https://api.douban.com/v2/book/"$line >> "$log"
		response=$(curl --silent "https://api.douban.com/v2/book/"$line)
		response=${response//"\/"/"/"}
		response=${response//"\\n"/"\\\\n"}
		msg=$(echo $response | grep '"msg":"[^"]*"')
		if [ -n "$msg" ]; then
			echo ""
			echo "[Failure] "$response | tee -a "$log"
			return 1
		else
			echo "done"
			echo "[Response] "$response >> "$log"
		fi
		title=$(echo $response | grep '"title":"[^"]*","url"' -Po | awk -F '"' '{print $4}')
		author=$(echo $response | grep '"author":\[[^\]]*\]' -Po | awk -F '\[|\]' '{print$2}' | tr -d '"')
		publisher=$(echo $response | grep '"publisher":"[^"]*"' -Po | awk -F  '"' '{print $4}')
		summary=$(echo $response | grep '"summary":"[^"]*"' -Po | awk -F  '"' '{print $4}')
		echo -e "ID: "$line", Title: "$title", Author(s): "$author", Publisher: "$publisher", Summary: "$summary"" >> $result
		sleep 1.5s
	done
	code=$?
	return $code
}

douban
code=$?
if [ $code = 0 ]; then
	rm "$list"
	echo "Results have been saved to \""$result"\"." | tee -a "$log"
fi
