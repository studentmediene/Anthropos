#!/bin/sh

# clean the project
rm -rf src/main/resources/local.properties
rm -rf target

# copy in production values
cp /srv/anthropos/local.properties src/main/resources/local.properties