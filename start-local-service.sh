#!/usr/bin/env bash

cp cloudfunctions/get-curr-data-pom.xml pom.xml
mvn function:run -Drun.functionTarget=stockdata.service.GetCurrDataService
rm pom.xml