用form表单传输

Request header

Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:69.0) Gecko/20100101 Firefox/69.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Content-Type: multipart/form-data; boundary=---------------------------1825888254439611312887262725
Content-Length: 292
Connection: keep-alive
Referer: http://localhost:8080/photo/upload
Upgrade-Insecure-Requests: 1

Request payload

-----------------------------1825888254439611312887262725
Content-Disposition: form-data; name="studentName"

safd
-----------------------------1825888254439611312887262725
Content-Disposition: form-data; name="aaaa"

asdf
-----------------------------1825888254439611312887262725--


用Json传输

Request header

Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:69.0) Gecko/20100101 Firefox/69.0
Accept: */*
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: http://localhost:8080/swagger-ui.html
Content-Type: application/json
Origin: http://localhost:8080
Content-Length: 29
Connection: keep-alive

Request payload
{
  "studentName": "safd",
  "aaaa":"asdf"
}


如果使用SpringBoot没有在参数上添加@RequestBody，数据应该从请求参数传过来

Request header
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:69.0) Gecko/20100101 Firefox/69.0
Accept: */*
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: http://localhost:8080/swagger-ui.html
Origin: http://localhost:8080
Connection: keep-alive
Content-Length: 0

query String
studentName: aaa
teacherName: eee

发送
curl -X POST "http://localhost:8080/photo/upload/actionf?studentName=aaa&teacherName=eee" -H  "accept: */*"

添加@RequestBody：只从request payload（请求体）中取值，请求头中必须有"Content-Type: application/json"，请求体不能为空
正常，能取到只curl -X POST "http://localhost:8080/photo/upload/actionf" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"studentName\": \"string\",  \"teacherName\": \"string\"}"

拿到的是请求体的值忽略请求参数值curl -X POST "http://localhost:8080/photo/upload/actionf?studentName=aaa&teacherName=eee" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"studentName\": \"string\",  \"teacherName\": \"string\"}"

下面两种是异常
curl -X POST "http://localhost:8080/photo/upload/actionf?studentName=aaa&teacherName=eee" -H  "accept: */*" -H  "Content-Type: application/json"
curl -X POST "http://localhost:8080/photo/upload/actionf?studentName=aaa&teacherName=eee" -H  "accept: */*"
{"timestamp":"2019-10-25T02:05:19.571+0000","status":400,"error":"Bad Request","message":"Required request body is missing: public java.lang.String com.tra.controller.UploadController.actionfile(com.tra.web.model.StudentForm,javax.servlet.http.HttpServletRequest)","path":"/photo/upload/actionf"}

不添加@RequestBody：从请求参数取值，或者使用multipart/form-data重请求体中获取Key Value

拿到的是请求参数值：curl -X POST "http://localhost:8080/photo/upload/actionf?studentName=aaa&teacherName=eee" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"studentName\": \"string\",  \"teacherName\": \"string\"}"
以下是没有请求参数，后台拿不到值
curl -X POST "http://localhost:8080/photo/upload/actionf" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"studentName\": \"string\",  \"teacherName\": \"string\"}"
curl -X POST "http://localhost:8080/photo/upload/actionf" -H  "accept: */*"
curl -X POST "http://localhost:8080/photo/upload/actionf" -H  "accept: */*" -H  "Content-Type: application/json"