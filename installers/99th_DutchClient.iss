; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "99th_DutchClient"
#define MyAppVersion "0.2.1"
#define MyAppPublisher "99th_Dutchies"
#define MyAppURL "https://www.99th-dutchies.nl/"
#define MCVersion "1.16.5"
#define MyDateTimeString GetDateTimeString('yyyymmddhhnnss', '', '')
#define BigDateTimeString GetDateTimeString('yyyy/mm/dd"T"hh:nn:ss".000Z"', '-', ':')

[Setup]
; NOTE: The value of AppId uniquely identifies this application. Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{2C25B067-D857-4A33-B295-BFF7CB110058} 
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={userappdata}\.minecraft\versions\{#MCVersion}-{#MyAppName}
DefaultGroupName={#MyAppName}
DisableProgramGroupPage=yes
; Remove the following line to run in administrative install mode (install for all users.)
PrivilegesRequired=lowest
OutputBaseFilename={#MyAppName}-{#MyAppVersion}-installer
Compression=lzma
SolidCompression=yes
WizardStyle=modern

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "{app}\..\{#MCVersion}\*"; DestDir: "{app}"; Flags: external recursesubdirs
Source: "C:\sources\99th_dutchclient\installers\{#MyAppName}-{#MyAppVersion}.jar"; DestDir: "{app}"; DestName: "{#MCVersion}-{#MyAppName}.jar"; AfterInstall: AfterDuplicate; Flags: ignoreversion

[Code]
procedure AfterDuplicate();
var jsonnewfilename: String;
var jsoncontent: AnsiString;
var jsoncontentuni: String;
var profilefilename: String;
var profilecontent: AnsiString;
var profilecontentuni: String;
var profilepos: Integer;
begin
  { Remove old .jar files }
  DeleteFile(ExpandConstant('{app}\{#MCVersion}.jar'));  

  { Rename .json file }
  jsonnewfilename := ExpandConstant('{app}\{#MCVersion}-{#MyAppName}.json');
  RenameFile(ExpandConstant('{app}\{#MCVersion}.json'), jsonnewfilename);

  { Read json file }
  LoadStringFromFile(jsonnewfilename, jsoncontent);
  jsoncontentuni := String(jsoncontent);
  
  { Remove downloads to prevent vanilla loading }
  StringChangeEx(jsoncontentuni, ', "downloads": {"client": {"sha1": "37fd3c903861eeff3bc24b71eed48f828b5269c8", "size": 17547153, "url": "https://launcher.mojang.com/v1/objects/37fd3c903861eeff3bc24b71eed48f828b5269c8/client.jar"}, "client_mappings": {"sha1": "374c6b789574afbdc901371207155661e0509e17", "size": 5746047, "url": "https://launcher.mojang.com/v1/objects/374c6b789574afbdc901371207155661e0509e17/client.txt"}, "server": {"sha1": "1b557e7b033b583cd9f66746b7a9ab1ec1673ced", "size": 37962360, "url": "https://launcher.mojang.com/v1/objects/1b557e7b033b583cd9f66746b7a9ab1ec1673ced/server.jar"}, "server_mappings": {"sha1": "41285beda6d251d190f2bf33beadd4fee187df7a", "size": 4400926, "url": "https://launcher.mojang.com/v1/objects/41285beda6d251d190f2bf33beadd4fee187df7a/server.txt"}}', '', True);
  { Update ID }
  StringChangeEx(jsoncontentuni, ', "id": "1.16.5"', ', "id": "{#MCVersion}-{#MyAppName}"', True);

  { Write back }
  SaveStringToFile(jsonnewfilename, AnsiString(jsoncontentuni), False);

  { Add profile }
  profilefilename := ExpandConstant('{app}\..\..\launcher_profiles.json');
  LoadStringFromFile(profilefilename, profilecontent);
  profilecontentuni := String(profilecontent);
  profilepos := Pos('"lastVersionId" : "{#MCVersion}-{#MyAppName}"', profilecontentuni);
  if profilepos = 0 then
  begin
    StringChangeEx(profilecontentuni, '"profiles" : {', '"profiles" : {"{#MyDateTimeString}" : {"created" : "{#BigDateTimeString}","icon" : "data:image/png;base64,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","lastUsed" : "1970-01-01T00:00:00.000Z","lastVersionId" : "{#MCVersion}-{#MyAppName}","name" : "{#MCVersion}-{#MyAppName}","type" : "custom"},', True);
    SaveStringToFile(profilefilename, AnsiString(profilecontentuni), False);
  end;
end;