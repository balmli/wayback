#!/bin/bash

. setenv.sh

pageSize=10
curlOpts=""
builddir=download

fetchUsers () {
  pageNumber=$1
  pageNumberPadded=`printf "%05d\n" $pageNumber`
  echo Users page: $pageNumberPadded
  url="$sc_url/users.json?page=$pageNumber&per_page=$pageSize"
  curl $curlOpts \
  -X GET -u $sc_username:$sc_password \
  $url > $builddir/users_$pageNumberPadded.json
  actualsize=$(wc -c < "$builddir/users_$pageNumberPadded.json"|cut -b 1-8)
  if [ $actualsize -le 60 ]; then
    eval "$2='exit'"
  fi
}

fetchAllUsers () {
  rm $builddir/users_*
  for i in $(seq 1 99999)
  do
    retval=""
    fetchUsers $i retval
    if [ "$retval" = "exit" ]; then
      break;
    fi
  done
}

fetchMessages () {
  pageNumber=$1
  pageNumberPadded=`printf "%05d\n" $pageNumber`
  echo Messages page: $pageNumberPadded
  url="$sc_url/messages.json?page=$pageNumber&per_page=$pageSize&comments_limit=999&likes_limit=999&comment_likes_limit=999"
  curl $curlOpts \
  -X GET -u $sc_username:$sc_password \
  $url > $builddir/sc_$pageNumberPadded.json
  actualsize=$(wc -c < "$builddir/sc_$pageNumberPadded.json"|cut -b 1-8)
  if [ $actualsize -le 60 ]; then
    eval "$2='exit'"
  fi
}

fetchAllMessages () {
  rm $builddir/sc_*
  for i in $(seq 1 99999)
  do
    retval=""
    fetchMessages $i retval
    if [ "$retval" = "exit" ]; then
      break;
    fi
  done
}

mkdir -p $builddir

fetchAllUsers
fetchAllMessages
