#!/bin/sh

cmd="$@"


scripts_dir="$LOLPAPERS_STUFF_PATH/init"
if [ -d "$scripts_dir" ]
then
	echo "Executing user scripts..."
	for f in $(find "$scripts_dir" -type f -name '*.sh')
	do
		echo "$f..."
		$f
	done
fi


until mysql -h "$LOLPAPERS_DS_HOST" -u "$LOLPAPERS_DS_USER" "$LOLPAPERS_DS_DB" "-p${LOLPAPERS_DS_PWD}" -e 'SELECT 1' > /dev/null
do
  echo "Mysql is unavailable - sleeping"
  sleep 1
done

echo "Mysql is up - executing command $cmd"
exec $cmd

