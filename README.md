# Protobuf huge message corruption

This is a simple project demonstrating how writting huge protobuf messages can lead to data corruption.

## Build the project
```
mvn clean install
```
## Run the scenario

The following command will create a huge message (~2.1GB) and write it to `target/book_file_160000000.pfile`.
Then will try to read back the message from the file and fail with an exception.
```
java -Xmx16g -cp target/protobuf-large-message-1.0-SddNAPSHOT-jar-with-dependencies.jar com.github.zabetak.protobuf.bugs.serde.WriteReadBook 160000000
```

### Stacktrace

```
Exception in thread "main" com.google.protobuf.InvalidProtocolBufferException: Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit.
	at com.google.protobuf.InvalidProtocolBufferException.sizeLimitExceeded(InvalidProtocolBufferException.java:162)
	at com.google.protobuf.CodedInputStream$StreamDecoder.refillBuffer(CodedInputStream.java:2781)
	at com.google.protobuf.CodedInputStream$StreamDecoder.readRawByte(CodedInputStream.java:2859)
	at com.google.protobuf.CodedInputStream$StreamDecoder.readRawVarint64SlowPath(CodedInputStream.java:2648)
	at com.google.protobuf.CodedInputStream$StreamDecoder.readRawVarint32(CodedInputStream.java:2542)
	at com.google.protobuf.CodedInputStream$StreamDecoder.readMessage(CodedInputStream.java:2405)
	at com.github.zabetak.protobuf.bugs.protos.AddressBook$Builder.mergeFrom(AddressBook.java:440)
	at com.github.zabetak.protobuf.bugs.protos.AddressBook$1.parsePartialFrom(AddressBook.java:742)
	at com.github.zabetak.protobuf.bugs.protos.AddressBook$1.parsePartialFrom(AddressBook.java:734)
	at com.google.protobuf.AbstractParser.parseFrom(AbstractParser.java:86)
	at com.google.protobuf.AbstractParser.parseFrom(AbstractParser.java:91)
	at com.google.protobuf.AbstractParser.parseFrom(AbstractParser.java:48)
	at com.google.protobuf.GeneratedMessageV3.parseWithIOException(GeneratedMessageV3.java:364)
	at com.github.zabetak.protobuf.bugs.protos.AddressBook.parseFrom(AddressBook.java:219)
	at com.github.zabetak.protobuf.bugs.serde.WriteReadBook.readBooks(WriteReadBook.java:63)
	at com.github.zabetak.protobuf.bugs.serde.WriteReadBook.main(WriteReadBook.java:35)
```
