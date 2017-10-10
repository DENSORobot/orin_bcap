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
    public partial class bCAPClient : IDisposable
    {
        public void Service_Start(string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.SERVICE_START,
                strOption));
            return;
        }

        public void Service_Stop()
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.SERVICE_STOP));
            return;
        }

        public int Controller_Connect(string strName, string strProvider, string strMachine = "", string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_CONNECT,
                strName, strProvider, strMachine, strOption));
            return (int)recv.aryArgs[0];
        }

        public void Controller_Disconnect(int iHandle)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_DISCONNECT,
                iHandle));
            return;
        }

        public int Controller_GetExtension(int iHandle, string strName, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETEXTENSION,
                iHandle, strName, strOption));
            return (int)recv.aryArgs[0];
        }

        public int Controller_GetFile(int iHandle, string strName, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETFILE,
                iHandle, strName, strOption));
            return (int)recv.aryArgs[0];
        }

        public int Controller_GetRobot(int iHandle, string strName, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETROBOT,
                iHandle, strName, strOption));
            return (int)recv.aryArgs[0];
        }

        public int Controller_GetTask(int iHandle, string strName, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETTASK,
                iHandle, strName, strOption));
            return (int)recv.aryArgs[0];
        }

        public int Controller_GetVariable(int iHandle, string strName, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETVARIABLE,
                iHandle, strName, strOption));
            return (int)recv.aryArgs[0];
        }

        public int Controller_GetCommand(int iHandle, string strName, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETCOMMAND,
                iHandle, strName, strOption));
            return (int)recv.aryArgs[0];
        }

        public object Controller_GetExtensionNames(int iHandle, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETEXTENSIONNAMES,
                iHandle, strOption));
            return recv.aryArgs[0];
        }

        public object Controller_GetFileNames(int iHandle, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETFILENAMES,
                iHandle, strOption));
            return recv.aryArgs[0];
        }

        public object Controller_GetRobotNames(int iHandle, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETROBOTNAMES,
                iHandle, strOption));
            return recv.aryArgs[0];
        }

        public object Controller_GetTaskNames(int iHandle, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETTASKNAMES,
                iHandle, strOption));
            return recv.aryArgs[0];
        }

        public object Controller_GetVariableNames(int iHandle, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETVARIABLENAMES,
                iHandle, strOption));
            return recv.aryArgs[0];
        }

        public object Controller_GetCommandNames(int iHandle, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETCOMMANDNAMES,
                iHandle, strOption));
            return recv.aryArgs[0];
        }

        public object Controller_Execute(int iHandle, string strCommand, object objParam = null)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_EXECUTE,
                iHandle, strCommand, objParam));
            return recv.aryArgs[0];
        }

        public int Controller_GetMessage(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETMESSAGE,
                iHandle));
            return (int)recv.aryArgs[0];
        }

        public int Controller_GetAttribute(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETATTRIBUTE,
               iHandle));
            return (int)recv.aryArgs[0];
        }

        public string Controller_GetHelp(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETHELP,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public string Controller_GetName(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETNAME,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public object Controller_GetTag(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETTAG,
                iHandle));
            return recv.aryArgs[0];
        }

        public void Controller_PutTag(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_PUTTAG,
                iHandle, newVal));
            return;
        }

        public object Controller_GetID(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_GETID,
                iHandle));
            return recv.aryArgs[0];
        }

        public void Controller_PutID(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.CONTROLLER_PUTID,
                iHandle, newVal));
            return;
        }

        public int Extension_GetVariable(int iHandle, string strName, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.EXTENSION_GETVARIABLE,
                iHandle, strName, strOption));
            return (int)recv.aryArgs[0];
        }

        public object Extension_GetVariableNames(int iHandle, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.EXTENSION_GETVARIABLENAMES,
               iHandle, strOption));
            return recv.aryArgs[0];
        }

        public object Extension_Execute(int iHandle, string strCommand, object objParam = null)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.EXTENSION_EXECUTE,
                iHandle, strCommand, objParam));
            return recv.aryArgs[0];
        }

        public int Extension_GetAttribute(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.EXTENSION_GETATTRIBUTE,
               iHandle));
            return (int)recv.aryArgs[0];
        }

        public string Extension_GetHelp(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.EXTENSION_GETHELP,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public string Extension_GetName(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.EXTENSION_GETNAME,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public object Extension_GetTag(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.EXTENSION_GETTAG,
               iHandle));
            return recv.aryArgs[0];
        }

        public void Extension_PutTag(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.EXTENSION_PUTTAG,
                iHandle, newVal));
            return;
        }

        public object Extension_GetID(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.EXTENSION_GETID,
                iHandle));
            return recv.aryArgs[0];
        }

        public void Extension_PutID(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.EXTENSION_PUTID,
                iHandle, newVal));
            return;
        }

        public void Extension_Release(int iHandle)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.EXTENSION_RELEASE,
                iHandle));
            return;
        }

        public int File_GetFile(int iHandle, string strName, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETFILE,
                iHandle, strName, strOption));
            return (int)recv.aryArgs[0];
        }

        public int File_GetVariable(int iHandle, string strName, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETVARIABLE,
                iHandle, strName, strOption));
            return (int)recv.aryArgs[0];
        }

        public object File_GetFileNames(int iHandle, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETFILENAMES,
               iHandle, strOption));
            return recv.aryArgs[0];
        }

        public object File_GetVariableNames(int iHandle, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETVARIABLENAMES,
               iHandle, strOption));
            return recv.aryArgs[0];
        }

        public object File_Execute(int iHandle, string strCommand, object objParam = null)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_EXECUTE,
               iHandle, strCommand, objParam));
            return recv.aryArgs[0];
        }

        public void File_Copy(int iHandle, string strName, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_COPY,
                iHandle, strName, strOption));
            return;
        }

        public void File_Delete(int iHandle, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_DELETE,
                iHandle, strOption));
            return;
        }

        public void File_Move(int iHandle, string strName, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_MOVE,
                iHandle, strName, strOption));
            return;
        }

        public string File_Run(int iHandle, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_RUN,
               iHandle, strOption));
            return (string)recv.aryArgs[0];
        }

        public object File_GetDateCreated(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETDATECREATED,
               iHandle));
            return recv.aryArgs[0];
        }

        public object File_GetDateLastAccessed(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETDATELASTACCESSED,
               iHandle));
            return recv.aryArgs[0];
        }

        public object File_GetDateLastModified(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETDATELASTMODIFIED,
               iHandle));
            return recv.aryArgs[0];
        }

        public string File_GetPath(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETPATH,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public int File_GetSize(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETSIZE,
               iHandle));
            return (int)recv.aryArgs[0];
        }

        public string File_GetType(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETTYPE,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public object File_GetValue(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETVALUE,
               iHandle));
            return recv.aryArgs[0];
        }

        public void File_PutValue(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_PUTVALUE,
               iHandle, newVal));
            return;
        }

        public int File_GetAttribute(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETATTRIBUTE,
               iHandle));
            return (int)recv.aryArgs[0];
        }

        public string File_GetHelp(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETHELP,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public string File_GetName(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETNAME,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public object File_GetTag(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETTAG,
               iHandle));
            return recv.aryArgs[0];
        }

        public void File_PutTag(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_PUTTAG,
               iHandle, newVal));
            return;
        }

        public object File_GetID(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_GETID,
               iHandle));
            return recv.aryArgs[0];
        }

        public void File_PutID(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_PUTID,
               iHandle, newVal));
            return;
        }

        public void File_Release(int iHandle)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.FILE_RELEASE,
               iHandle));
            return;
        }

        public int Robot_GetVariable(int iHandle, string strName, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_GETVARIABLE,
                iHandle, strName, strOption));
            return (int)recv.aryArgs[0];
        }

        public object Robot_GetVariableNames(int iHandle, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_GETVARIABLENAMES,
               iHandle, strOption));
            return recv.aryArgs[0];
        }

        public object Robot_Execute(int iHandle, string strCommand, object objParam = null)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_EXECUTE,
               iHandle, strCommand, objParam));
            return recv.aryArgs[0];
        }

        public void Robot_Accelerate(int iHandle, int iAxis, float fAccel, float fDecel)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_ACCELERATE,
               iHandle, iAxis, fAccel, fDecel));
            return;
        }

        public void Robot_Change(int iHandle, string strName)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_CHANGE,
               iHandle, strName));
            return;
        }

        public void Robot_Chuck(int iHandle, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_CHUCK,
               iHandle, strOption));
            return;
        }

        public void Robot_Drive(int iHandle, int iAxis, float fMov, String strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_DRIVE,
               iHandle, iAxis, fMov, strOption));
            return;
        }

        public void Robot_GoHome(int iHandle)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_GOHOME,
               iHandle));
            return;
        }

        public void Robot_Halt(int iHandle, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_HALT,
               iHandle, strOption));
            return;
        }

        public void Robot_Hold(int iHandle, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_HOLD,
               iHandle, strOption));
            return;
        }

        public void Robot_Move(int iHandle, int iComp, object objPose, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_MOVE,
               iHandle, iComp, objPose, strOption));
            return;
        }

        public void Robot_Rotate(int iHandle, object objRotSuf, float fDeg, object objPivot, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_ROTATE,
               iHandle, objRotSuf, fDeg, objPivot, strOption));
            return;
        }

        public void Robot_Speed(int iHandle, int iAxis, float fSpeed)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_SPEED,
               iHandle, iAxis, fSpeed));
            return;
        }

        public void Robot_Unchuck(int iHandle, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_UNCHUCK,
               iHandle, strOption));
            return;
        }

        public void Robot_Unhold(int iHandle, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_UNHOLD,
               iHandle, strOption));
            return;
        }

        public int Robot_GetAttribute(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_GETATTRIBUTE,
               iHandle));
            return (int)recv.aryArgs[0];
        }

        public string Robot_GetHelp(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_GETHELP,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public string Robot_GetName(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_GETNAME,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public object Robot_GetTag(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_GETTAG,
               iHandle));
            return recv.aryArgs[0];
        }

        public void Robot_PutTag(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_PUTTAG,
               iHandle, newVal));
            return;
        }

        public object Robot_GetID(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_GETID,
               iHandle));
            return recv.aryArgs[0];
        }

        public void Robot_PutID(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_PUTID,
               iHandle, newVal));
            return;
        }

        public void Robot_Release(int iHandle)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.ROBOT_RELEASE,
               iHandle));
            return;
        }

        public int Task_GetVariable(int iHandle, string strName, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_GETVARIABLE,
                iHandle, strName, strOption));
            return (int)recv.aryArgs[0];
        }

        public object Task_GetVariableNames(int iHandle, string strOption = "")
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_GETVARIABLENAMES,
               iHandle, strOption));
            return recv.aryArgs[0];
        }

        public object Task_Execute(int iHandle, string strCommand, object objParam = null)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_EXECUTE,
               iHandle, strCommand, objParam));
            return recv.aryArgs[0];
        }

        public void Task_Start(int iHandle, int iMode, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_START,
               iHandle, iMode, strOption));
            return;
        }

        public void Task_Stop(int iHandle, int iMode, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_STOP,
               iHandle, iMode, strOption));
            return;
        }

        public void Task_Delete(int iHandle, string strOption = "")
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_DELETE,
               iHandle, strOption));
            return;
        }

        public string Task_GetFileName(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_GETFILENAME,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public int Task_GetAttribute(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_GETATTRIBUTE,
               iHandle));
            return (int)recv.aryArgs[0];
        }

        public string Task_GetHelp(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_GETHELP,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public string Task_GetName(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_GETNAME,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public object Task_GetTag(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_GETTAG,
               iHandle));
            return recv.aryArgs[0];
        }

        public void Task_PutTag(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_PUTTAG,
               iHandle, newVal));
            return;
        }

        public object Task_GetID(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_GETID,
               iHandle));
            return recv.aryArgs[0];
        }

        public void Task_PutID(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_PUTID,
               iHandle, newVal));
            return;
        }

        public void Task_Release(int iHandle)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.TASK_RELEASE,
               iHandle));
            return;
        }

        public object Variable_GetDateTime(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.VARIABLE_GETDATETIME,
               iHandle));
            return recv.aryArgs[0];
        }

        public object Variable_GetValue(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.VARIABLE_GETVALUE,
               iHandle));
            return recv.aryArgs[0];
        }

        public void Variable_PutValue(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.VARIABLE_PUTVALUE,
               iHandle, newVal));
            return;
        }

        public int Variable_GetAttribute(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.VARIABLE_GETATTRIBUTE,
               iHandle));
            return (int)recv.aryArgs[0];
        }

        public string Variable_GetHelp(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.VARIABLE_GETHELP,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public string Variable_GetName(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.VARIABLE_GETNAME,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public object Variable_GetTag(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.VARIABLE_GETTAG,
               iHandle));
            return recv.aryArgs[0];
        }

        public void Variable_PutTag(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.VARIABLE_PUTTAG,
               iHandle, newVal));
            return;
        }

        public object Variable_GetID(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.VARIABLE_GETID,
               iHandle));
            return recv.aryArgs[0];
        }

        public void Variable_PutID(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.VARIABLE_PUTID,
               iHandle, newVal));
            return;
        }

        public int Variable_GetMicrosecond(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.VARIABLE_GETMICROSECOND,
               iHandle));
            return (int)recv.aryArgs[0];
        }

        public void Variable_Release(int iHandle)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.VARIABLE_RELEASE,
               iHandle));
            return;
        }

        public void Command_Execute(int iHandle, int iMode)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_EXECUTE,
               iHandle, iMode));
            return;
        }

        public void Command_Cancel(int iHandle)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_CANCEL,
               iHandle));
            return;
        }

        public int Command_GetTimeout(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_GETTIMEOUT,
               iHandle));
            return (int)recv.aryArgs[0];
        }

        public void Command_PutTimeout(int iHandle, int newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_PUTTIMEOUT,
               iHandle, newVal));
            return;
        }

        public int Command_GetState(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_GETSTATE,
               iHandle));
            return (int)recv.aryArgs[0];
        }

        public object Command_GetParameters(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_GETPARAMETERS,
               iHandle));
            return recv.aryArgs[0];
        }

        public void Command_PutParameters(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_PUTPARAMETERS,
               iHandle, newVal));
            return;
        }

        public object Command_GetResult(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_GETRESULT,
               iHandle));
            return recv.aryArgs[0];
        }

        public int Command_GetAttribute(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_GETATTRIBUTE,
               iHandle));
            return (int)recv.aryArgs[0];
        }

        public string Command_GetHelp(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_GETHELP,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public string Command_GetName(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_GETNAME,
               iHandle));
            return (string)recv.aryArgs[0];
        }

        public object Command_GetTag(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_GETTAG,
               iHandle));
            return recv.aryArgs[0];
        }

        public void Command_PutTag(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_PUTTAG,
               iHandle, newVal));
            return;
        }

        public object Command_GetID(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_GETID,
               iHandle));
            return recv.aryArgs[0];
        }

        public void Command_PutID(int iHandle, object newVal)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_PUTID,
               iHandle, newVal));
            return;
        }

        public void Command_Release(int iHandle)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.COMMAND_RELEASE,
               iHandle));
            return;
        }

        public void Message_Reply(int iHandle, object objData)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.MESSAGE_REPLY,
               iHandle, objData));
            return;
        }

        public void Message_Clear(int iHandle)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.MESSAGE_CLEAR,
               iHandle));
            return;
        }

        public object Message_GetDateTime(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.MESSAGE_GETDATETIME,
                iHandle));
            return recv.aryArgs[0];
        }

        public string Message_GetDescription(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.MESSAGE_GETDESCRIPTION,
                iHandle));
            return (string)recv.aryArgs[0];
        }

        public string Message_GetDestination(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.MESSAGE_GETDESTINATION,
                iHandle));
            return (string)recv.aryArgs[0];
        }

        public int Message_GetNumber(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.MESSAGE_GETNUMBER,
                iHandle));
            return (int)recv.aryArgs[0];
        }

        public int Message_GetSerialNumber(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.MESSAGE_GETSERIALNUMBER,
                iHandle));
            return (int)recv.aryArgs[0];
        }

        public string Message_GetSource(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.MESSAGE_GETSOURCE,
                iHandle));
            return (string)recv.aryArgs[0];
        }

        public object Message_GetValue(int iHandle)
        {
            var recv = m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.MESSAGE_GETVALUE,
                iHandle));
            return recv.aryArgs[0];
        }

        public void Message_Release(int iHandle)
        {
            m_conn.SendAndRecv(new bCAPPacket(bCAPDefine.MESSAGE_RELEASE,
               iHandle));
            return;
        }
    }
}
