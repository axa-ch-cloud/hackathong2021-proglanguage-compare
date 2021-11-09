package db

import (
	"encoding/json"
	"fmt"
	"io/ioutil"

	uuid2 "github.com/google/uuid"
)

const (
	perm = 0777
	dir  = "./db/"
)

func Store(entity Entity) (string, error) {
	uuid := uuid2.NewString()
	filename := fmt.Sprintf("%s%s.json", dir, uuid)
	jsonData, err := json.Marshal(entity)
	if err != nil {
		panic(fmt.Sprintf("%v", err))
	}

	err = ioutil.WriteFile(filename, jsonData, perm)
	return uuid, err
}

func Get(uuid string) ([]byte, error) {
	filename := fmt.Sprintf("%s%s.json", dir, uuid)
	return ioutil.ReadFile(filename)
}
