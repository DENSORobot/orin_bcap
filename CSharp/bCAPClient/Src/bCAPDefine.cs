/**
 * Software License Agreement (MIT License)
 *
 * @copyright Copyright (c) 2015 DENSO WAVE INCORPORATED
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ORiN2.bCAP
{
    static class bCAPDefine
    {
        public const int SERVICE_START = 1;
        public const int SERVICE_STOP = 2;
        public const int CONTROLLER_CONNECT = 3;
        public const int CONTROLLER_DISCONNECT = 4;
        public const int CONTROLLER_GETEXTENSION = 5;
        public const int CONTROLLER_GETFILE = 6;
        public const int CONTROLLER_GETROBOT = 7;
        public const int CONTROLLER_GETTASK = 8;
        public const int CONTROLLER_GETVARIABLE = 9;
        public const int CONTROLLER_GETCOMMAND = 10;
        public const int CONTROLLER_GETEXTENSIONNAMES = 11;
        public const int CONTROLLER_GETFILENAMES = 12;
        public const int CONTROLLER_GETROBOTNAMES = 13;
        public const int CONTROLLER_GETTASKNAMES = 14;
        public const int CONTROLLER_GETVARIABLENAMES = 15;
        public const int CONTROLLER_GETCOMMANDNAMES = 16;
        public const int CONTROLLER_EXECUTE = 17;
        public const int CONTROLLER_GETMESSAGE = 18;
        public const int CONTROLLER_GETATTRIBUTE = 19;
        public const int CONTROLLER_GETHELP = 20;
        public const int CONTROLLER_GETNAME = 21;
        public const int CONTROLLER_GETTAG = 22;
        public const int CONTROLLER_PUTTAG = 23;
        public const int CONTROLLER_GETID = 24;
        public const int CONTROLLER_PUTID = 25;
        public const int EXTENSION_GETVARIABLE = 26;
        public const int EXTENSION_GETVARIABLENAMES = 27;
        public const int EXTENSION_EXECUTE = 28;
        public const int EXTENSION_GETATTRIBUTE = 29;
        public const int EXTENSION_GETHELP = 30;
        public const int EXTENSION_GETNAME = 31;
        public const int EXTENSION_GETTAG = 32;
        public const int EXTENSION_PUTTAG = 33;
        public const int EXTENSION_GETID = 34;
        public const int EXTENSION_PUTID = 35;
        public const int EXTENSION_RELEASE = 36;
        public const int FILE_GETFILE = 37;
        public const int FILE_GETVARIABLE = 38;
        public const int FILE_GETFILENAMES = 39;
        public const int FILE_GETVARIABLENAMES = 40;
        public const int FILE_EXECUTE = 41;
        public const int FILE_COPY = 42;
        public const int FILE_DELETE = 43;
        public const int FILE_MOVE = 44;
        public const int FILE_RUN = 45;
        public const int FILE_GETDATECREATED = 46;
        public const int FILE_GETDATELASTACCESSED = 47;
        public const int FILE_GETDATELASTMODIFIED = 48;
        public const int FILE_GETPATH = 49;
        public const int FILE_GETSIZE = 50;
        public const int FILE_GETTYPE = 51;
        public const int FILE_GETVALUE = 52;
        public const int FILE_PUTVALUE = 53;
        public const int FILE_GETATTRIBUTE = 54;
        public const int FILE_GETHELP = 55;
        public const int FILE_GETNAME = 56;
        public const int FILE_GETTAG = 57;
        public const int FILE_PUTTAG = 58;
        public const int FILE_GETID = 59;
        public const int FILE_PUTID = 60;
        public const int FILE_RELEASE = 61;
        public const int ROBOT_GETVARIABLE = 62;
        public const int ROBOT_GETVARIABLENAMES = 63;
        public const int ROBOT_EXECUTE = 64;
        public const int ROBOT_ACCELERATE = 65;
        public const int ROBOT_CHANGE = 66;
        public const int ROBOT_CHUCK = 67;
        public const int ROBOT_DRIVE = 68;
        public const int ROBOT_GOHOME = 69;
        public const int ROBOT_HALT = 70;
        public const int ROBOT_HOLD = 71;
        public const int ROBOT_MOVE = 72;
        public const int ROBOT_ROTATE = 73;
        public const int ROBOT_SPEED = 74;
        public const int ROBOT_UNCHUCK = 75;
        public const int ROBOT_UNHOLD = 76;
        public const int ROBOT_GETATTRIBUTE = 77;
        public const int ROBOT_GETHELP = 78;
        public const int ROBOT_GETNAME = 79;
        public const int ROBOT_GETTAG = 80;
        public const int ROBOT_PUTTAG = 81;
        public const int ROBOT_GETID = 82;
        public const int ROBOT_PUTID = 83;
        public const int ROBOT_RELEASE = 84;
        public const int TASK_GETVARIABLE = 85;
        public const int TASK_GETVARIABLENAMES = 86;
        public const int TASK_EXECUTE = 87;
        public const int TASK_START = 88;
        public const int TASK_STOP = 89;
        public const int TASK_DELETE = 90;
        public const int TASK_GETFILENAME = 91;
        public const int TASK_GETATTRIBUTE = 92;
        public const int TASK_GETHELP = 93;
        public const int TASK_GETNAME = 94;
        public const int TASK_GETTAG = 95;
        public const int TASK_PUTTAG = 96;
        public const int TASK_GETID = 97;
        public const int TASK_PUTID = 98;
        public const int TASK_RELEASE = 99;
        public const int VARIABLE_GETDATETIME = 100;
        public const int VARIABLE_GETVALUE = 101;
        public const int VARIABLE_PUTVALUE = 102;
        public const int VARIABLE_GETATTRIBUTE = 103;
        public const int VARIABLE_GETHELP = 104;
        public const int VARIABLE_GETNAME = 105;
        public const int VARIABLE_GETTAG = 106;
        public const int VARIABLE_PUTTAG = 107;
        public const int VARIABLE_GETID = 108;
        public const int VARIABLE_PUTID = 109;
        public const int VARIABLE_GETMICROSECOND = 110;
        public const int VARIABLE_RELEASE = 111;
        public const int COMMAND_EXECUTE = 112;
        public const int COMMAND_CANCEL = 113;
        public const int COMMAND_GETTIMEOUT = 114;
        public const int COMMAND_PUTTIMEOUT = 115;
        public const int COMMAND_GETSTATE = 116;
        public const int COMMAND_GETPARAMETERS = 117;
        public const int COMMAND_PUTPARAMETERS = 118;
        public const int COMMAND_GETRESULT = 119;
        public const int COMMAND_GETATTRIBUTE = 120;
        public const int COMMAND_GETHELP = 121;
        public const int COMMAND_GETNAME = 122;
        public const int COMMAND_GETTAG = 123;
        public const int COMMAND_PUTTAG = 124;
        public const int COMMAND_GETID = 125;
        public const int COMMAND_PUTID = 126;
        public const int COMMAND_RELEASE = 127;
        public const int MESSAGE_REPLY = 128;
        public const int MESSAGE_CLEAR = 129;
        public const int MESSAGE_GETDATETIME = 130;
        public const int MESSAGE_GETDESCRIPTION = 131;
        public const int MESSAGE_GETDESTINATION = 132;
        public const int MESSAGE_GETNUMBER = 133;
        public const int MESSAGE_GETSERIALNUMBER = 134;
        public const int MESSAGE_GETSOURCE = 135;
        public const int MESSAGE_GETVALUE = 136;
        public const int MESSAGE_RELEASE = 137;
    }
}
