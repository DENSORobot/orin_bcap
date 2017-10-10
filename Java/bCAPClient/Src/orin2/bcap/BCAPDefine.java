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

package orin2.bcap;

class BCAPDefine {
    public static final int SERVICE_START = 1;
    public static final int SERVICE_STOP = 2;
    public static final int CONTROLLER_CONNECT = 3;
    public static final int CONTROLLER_DISCONNECT = 4;
    public static final int CONTROLLER_GETEXTENSION = 5;
    public static final int CONTROLLER_GETFILE = 6;
    public static final int CONTROLLER_GETROBOT = 7;
    public static final int CONTROLLER_GETTASK = 8;
    public static final int CONTROLLER_GETVARIABLE = 9;
    public static final int CONTROLLER_GETCOMMAND = 10;
    public static final int CONTROLLER_GETEXTENSIONNAMES = 11;
    public static final int CONTROLLER_GETFILENAMES = 12;
    public static final int CONTROLLER_GETROBOTNAMES = 13;
    public static final int CONTROLLER_GETTASKNAMES = 14;
    public static final int CONTROLLER_GETVARIABLENAMES = 15;
    public static final int CONTROLLER_GETCOMMANDNAMES = 16;
    public static final int CONTROLLER_EXECUTE = 17;
    public static final int CONTROLLER_GETMESSAGE = 18;
    public static final int CONTROLLER_GETATTRIBUTE = 19;
    public static final int CONTROLLER_GETHELP = 20;
    public static final int CONTROLLER_GETNAME = 21;
    public static final int CONTROLLER_GETTAG = 22;
    public static final int CONTROLLER_PUTTAG = 23;
    public static final int CONTROLLER_GETID = 24;
    public static final int CONTROLLER_PUTID = 25;
    public static final int EXTENSION_GETVARIABLE = 26;
    public static final int EXTENSION_GETVARIABLENAMES = 27;
    public static final int EXTENSION_EXECUTE = 28;
    public static final int EXTENSION_GETATTRIBUTE = 29;
    public static final int EXTENSION_GETHELP = 30;
    public static final int EXTENSION_GETNAME = 31;
    public static final int EXTENSION_GETTAG = 32;
    public static final int EXTENSION_PUTTAG = 33;
    public static final int EXTENSION_GETID = 34;
    public static final int EXTENSION_PUTID = 35;
    public static final int EXTENSION_RELEASE = 36;
    public static final int FILE_GETFILE = 37;
    public static final int FILE_GETVARIABLE = 38;
    public static final int FILE_GETFILENAMES = 39;
    public static final int FILE_GETVARIABLENAMES = 40;
    public static final int FILE_EXECUTE = 41;
    public static final int FILE_COPY = 42;
    public static final int FILE_DELETE = 43;
    public static final int FILE_MOVE = 44;
    public static final int FILE_RUN = 45;
    public static final int FILE_GETDATECREATED = 46;
    public static final int FILE_GETDATELASTACCESSED = 47;
    public static final int FILE_GETDATELASTMODIFIED = 48;
    public static final int FILE_GETPATH = 49;
    public static final int FILE_GETSIZE = 50;
    public static final int FILE_GETTYPE = 51;
    public static final int FILE_GETVALUE = 52;
    public static final int FILE_PUTVALUE = 53;
    public static final int FILE_GETATTRIBUTE = 54;
    public static final int FILE_GETHELP = 55;
    public static final int FILE_GETNAME = 56;
    public static final int FILE_GETTAG = 57;
    public static final int FILE_PUTTAG = 58;
    public static final int FILE_GETID = 59;
    public static final int FILE_PUTID = 60;
    public static final int FILE_RELEASE = 61;
    public static final int ROBOT_GETVARIABLE = 62;
    public static final int ROBOT_GETVARIABLENAMES = 63;
    public static final int ROBOT_EXECUTE = 64;
    public static final int ROBOT_ACCELERATE = 65;
    public static final int ROBOT_CHANGE = 66;
    public static final int ROBOT_CHUCK = 67;
    public static final int ROBOT_DRIVE = 68;
    public static final int ROBOT_GOHOME = 69;
    public static final int ROBOT_HALT = 70;
    public static final int ROBOT_HOLD = 71;
    public static final int ROBOT_MOVE = 72;
    public static final int ROBOT_ROTATE = 73;
    public static final int ROBOT_SPEED = 74;
    public static final int ROBOT_UNCHUCK = 75;
    public static final int ROBOT_UNHOLD = 76;
    public static final int ROBOT_GETATTRIBUTE = 77;
    public static final int ROBOT_GETHELP = 78;
    public static final int ROBOT_GETNAME = 79;
    public static final int ROBOT_GETTAG = 80;
    public static final int ROBOT_PUTTAG = 81;
    public static final int ROBOT_GETID = 82;
    public static final int ROBOT_PUTID = 83;
    public static final int ROBOT_RELEASE = 84;
    public static final int TASK_GETVARIABLE = 85;
    public static final int TASK_GETVARIABLENAMES = 86;
    public static final int TASK_EXECUTE = 87;
    public static final int TASK_START = 88;
    public static final int TASK_STOP = 89;
    public static final int TASK_DELETE = 90;
    public static final int TASK_GETFILENAME = 91;
    public static final int TASK_GETATTRIBUTE = 92;
    public static final int TASK_GETHELP = 93;
    public static final int TASK_GETNAME = 94;
    public static final int TASK_GETTAG = 95;
    public static final int TASK_PUTTAG = 96;
    public static final int TASK_GETID = 97;
    public static final int TASK_PUTID = 98;
    public static final int TASK_RELEASE = 99;
    public static final int VARIABLE_GETDATETIME = 100;
    public static final int VARIABLE_GETVALUE = 101;
    public static final int VARIABLE_PUTVALUE = 102;
    public static final int VARIABLE_GETATTRIBUTE = 103;
    public static final int VARIABLE_GETHELP = 104;
    public static final int VARIABLE_GETNAME = 105;
    public static final int VARIABLE_GETTAG = 106;
    public static final int VARIABLE_PUTTAG = 107;
    public static final int VARIABLE_GETID = 108;
    public static final int VARIABLE_PUTID = 109;
    public static final int VARIABLE_GETMICROSECOND = 110;
    public static final int VARIABLE_RELEASE = 111;
    public static final int COMMAND_EXECUTE = 112;
    public static final int COMMAND_CANCEL = 113;
    public static final int COMMAND_GETTIMEOUT = 114;
    public static final int COMMAND_PUTTIMEOUT = 115;
    public static final int COMMAND_GETSTATE = 116;
    public static final int COMMAND_GETPARAMETERS = 117;
    public static final int COMMAND_PUTPARAMETERS = 118;
    public static final int COMMAND_GETRESULT = 119;
    public static final int COMMAND_GETATTRIBUTE = 120;
    public static final int COMMAND_GETHELP = 121;
    public static final int COMMAND_GETNAME = 122;
    public static final int COMMAND_GETTAG = 123;
    public static final int COMMAND_PUTTAG = 124;
    public static final int COMMAND_GETID = 125;
    public static final int COMMAND_PUTID = 126;
    public static final int COMMAND_RELEASE = 127;
    public static final int MESSAGE_REPLY = 128;
    public static final int MESSAGE_CLEAR = 129;
    public static final int MESSAGE_GETDATETIME = 130;
    public static final int MESSAGE_GETDESCRIPTION = 131;
    public static final int MESSAGE_GETDESTINATION = 132;
    public static final int MESSAGE_GETNUMBER = 133;
    public static final int MESSAGE_GETSERIALNUMBER = 134;
    public static final int MESSAGE_GETSOURCE = 135;
    public static final int MESSAGE_GETVALUE = 136;
    public static final int MESSAGE_RELEASE = 137;
}
