#!/usr/bin/env bash

if $1 == "1" ;then
echo "开始清除缓存"
rm -rf $HOME/.gradle/caches/
rm -rf ./gradle/
echo "全部清除"
fi

echo "---------------从新编译---------------------"
sudo ./gradlew clean build
