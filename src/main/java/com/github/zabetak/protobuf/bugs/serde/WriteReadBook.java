/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.zabetak.protobuf.bugs.serde;

import com.github.zabetak.protobuf.bugs.protos.AddressBook;
import com.github.zabetak.protobuf.bugs.protos.Person;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriteReadBook {

  public static void main(String[] args) throws Exception {
    long count = Long.parseLong(args[0]);
    writeBooks(count);
    readBooks(count);
  }

  private static void writeBooks(long count) throws IOException {
    Path filepath = filepath(count);
    if (Files.exists(filepath)) {
      return;
    }
    AddressBook.Builder addressBook = AddressBook.newBuilder();

    String name = "Stamatis Z"; // 10bytes

    for (long i = 0; i < count; i++) {
      Person.Builder person = Person.newBuilder();
      person.setName(name);
      addressBook.addPeople(person.build());
    }
    AddressBook book = addressBook.build();

    try (FileOutputStream fos = new FileOutputStream(filepath.toString())) {
      CodedOutputStream cos = CodedOutputStream.newInstance(fos);
      book.writeTo(cos);
      cos.flush();
    }
  }

  private static void readBooks(long count) throws IOException {
    try (FileInputStream fis = new FileInputStream(filepath(count).toString())) {
      AddressBook book = AddressBook.parseFrom(CodedInputStream.newInstance(fis));
      System.out.println(book.getPeopleCount());
    }
  }

  private static Path filepath(long count) {
    return Path.of("target", "book_file_" + count + ".pfile");
  }
}
