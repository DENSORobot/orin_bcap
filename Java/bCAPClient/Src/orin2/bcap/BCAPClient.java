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

import orin2.library.ConnOptParser;

public class BCAPClient {
	private BCAPConnectionBase m_conn = null;
	
    public BCAPClient(String strConn, int timeout, int retry) throws Throwable {
        if (strConn.length() < 3) throw new IllegalArgumentException();

        String strOpt = strConn.substring(0, 3).toLowerCase();
        if(strOpt.equals("tcp")) {
        	Object[] obj = ConnOptParser.ParseEtherOption(strConn);
        	m_conn = new BCAPConnectionTCP((String)obj[0], (Integer)obj[1], timeout, retry);
        }
        else if(strOpt.equals("udp")) {
        	Object[] obj = ConnOptParser.ParseEtherOption(strConn);
        	m_conn = new BCAPConnectionUDP((String)obj[0], (Integer)obj[1], timeout, retry);
        }
        else {
        	throw new IllegalArgumentException();
        }
    }

    public void Release() {
    	if(m_conn != null) {
	        m_conn.Release();
	        m_conn = null;
    	}
    }

    public int GetTimeout() {
        return m_conn.GetTimeout();
    }

    public void SetTimeout(int timeout) throws Throwable {
        m_conn.SetTimeout(timeout);
    }

    public int GetRetry() {
        return m_conn.GetRetry();
    }

    public void SetRetry(int retry) throws Throwable {
        m_conn.SetRetry(retry);
    }
    
    public void Service_Start(String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.SERVICE_START,
            new Object[]{  strOption }));
        return;
    }

    public void Service_Stop() throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.SERVICE_STOP, null));
        return;
    }

    public int Controller_Connect(String strName, String strProvider, String strMachine, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_CONNECT,
            new Object[]{ strName, strProvider, strMachine, strOption }));
        return (Integer)recv.Args.get(0);
    }

    public void Controller_Disconnect(int iHandle) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_DISCONNECT,
            new Object[]{ iHandle }));
        return;
    }

    public int Controller_GetExtension(int iHandle, String strName, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETEXTENSION,
            new Object[]{ iHandle, strName, strOption }));
        return (Integer)recv.Args.get(0);
    }

    public int Controller_GetFile(int iHandle, String strName, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETFILE,
            new Object[]{ iHandle, strName, strOption }));
        return (Integer)recv.Args.get(0);
    }

    public int Controller_GetRobot(int iHandle, String strName, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETROBOT,
            new Object[]{ iHandle, strName, strOption }));
        return (Integer)recv.Args.get(0);
    }

    public int Controller_GetTask(int iHandle, String strName, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETTASK,
            new Object[]{ iHandle, strName, strOption }));
        return (Integer)recv.Args.get(0);
    }

    public int Controller_GetVariable(int iHandle, String strName, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETVARIABLE,
            new Object[]{ iHandle, strName, strOption }));
        return (Integer)recv.Args.get(0);
    }

    public int Controller_GetCommand(int iHandle, String strName, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETCOMMAND,
            new Object[]{ iHandle, strName, strOption }));
        return (Integer)recv.Args.get(0);
    }

    public Object Controller_GetExtensionNames(int iHandle, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETEXTENSIONNAMES,
            new Object[]{ iHandle, strOption }));
        return recv.Args.get(0);
    }

    public Object Controller_GetFileNames(int iHandle, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETFILENAMES,
            new Object[]{ iHandle, strOption }));
        return recv.Args.get(0);
    }

    public Object Controller_GetRobotNames(int iHandle, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETROBOTNAMES,
            new Object[]{ iHandle, strOption }));
        return recv.Args.get(0);
    }

    public Object Controller_GetTaskNames(int iHandle, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETTASKNAMES,
            new Object[]{ iHandle, strOption }));
        return recv.Args.get(0);
    }

    public Object Controller_GetVariableNames(int iHandle, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETVARIABLENAMES,
            new Object[]{ iHandle, strOption }));
        return recv.Args.get(0);
    }

    public Object Controller_GetCommandNames(int iHandle, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETCOMMANDNAMES,
            new Object[]{ iHandle, strOption }));
        return recv.Args.get(0);
    }

    public Object Controller_Execute(int iHandle, String strCommand, Object objParam) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_EXECUTE,
            new Object[]{ iHandle, strCommand, objParam }));
        return recv.Args.get(0);
    }

    public int Controller_GetMessage(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETMESSAGE,
            new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public int Controller_GetAttribute(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETATTRIBUTE,
           new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public String Controller_GetHelp(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETHELP,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public String Controller_GetName(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETNAME,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public Object Controller_GetTag(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETTAG,
            new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Controller_PutTag(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_PUTTAG,
            new Object[]{ iHandle, newVal }));
        return;
    }

    public Object Controller_GetID(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_GETID,
            new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Controller_PutID(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.CONTROLLER_PUTID,
            new Object[]{ iHandle, newVal }));
        return;
    }

    public int Extension_GetVariable(int iHandle, String strName, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.EXTENSION_GETVARIABLE,
            new Object[]{ iHandle, strName, strOption }));
        return (Integer)recv.Args.get(0);
    }

    public Object Extension_GetVariableNames(int iHandle, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.EXTENSION_GETVARIABLENAMES,
           new Object[]{ iHandle, strOption }));
        return recv.Args.get(0);
    }

    public Object Extension_Execute(int iHandle, String strCommand, Object objParam) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.EXTENSION_EXECUTE,
            new Object[]{ iHandle, strCommand, objParam }));
        return recv.Args.get(0);
    }

    public int Extension_GetAttribute(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.EXTENSION_GETATTRIBUTE,
           new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public String Extension_GetHelp(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.EXTENSION_GETHELP,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public String Extension_GetName(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.EXTENSION_GETNAME,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public Object Extension_GetTag(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.EXTENSION_GETTAG,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Extension_PutTag(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.EXTENSION_PUTTAG,
            new Object[]{ iHandle, newVal }));
        return;
    }

    public Object Extension_GetID(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.EXTENSION_GETID,
            new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Extension_PutID(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.EXTENSION_PUTID,
            new Object[]{ iHandle, newVal }));
        return;
    }

    public void Extension_Release(int iHandle) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.EXTENSION_RELEASE,
            new Object[]{ iHandle }));
        return;
    }

    public int File_GetFile(int iHandle, String strName, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETFILE,
            new Object[]{ iHandle, strName, strOption }));
        return (Integer)recv.Args.get(0);
    }

    public int File_GetVariable(int iHandle, String strName, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETVARIABLE,
            new Object[]{ iHandle, strName, strOption }));
        return (Integer)recv.Args.get(0);
    }

    public Object File_GetFileNames(int iHandle, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETFILENAMES,
           new Object[]{ iHandle, strOption }));
        return recv.Args.get(0);
    }

    public Object File_GetVariableNames(int iHandle, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETVARIABLENAMES,
           new Object[]{ iHandle, strOption }));
        return recv.Args.get(0);
    }

    public Object File_Execute(int iHandle, String strCommand, Object objParam) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_EXECUTE,
           new Object[]{ iHandle, strCommand, objParam }));
        return recv.Args.get(0);
    }

    public void File_Copy(int iHandle, String strName, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_COPY,
            new Object[]{ iHandle, strName, strOption }));
        return;
    }

    public void File_Delete(int iHandle, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_DELETE,
            new Object[]{ iHandle, strOption }));
        return;
    }

    public void File_Move(int iHandle, String strName, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_MOVE,
            new Object[]{ iHandle, strName, strOption }));
        return;
    }

    public String File_Run(int iHandle, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_RUN,
           new Object[]{ iHandle, strOption }));
        return (String)recv.Args.get(0);
    }

    public Object File_GetDateCreated(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETDATECREATED,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public Object File_GetDateLastAccessed(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETDATELASTACCESSED,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public Object File_GetDateLastModified(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETDATELASTMODIFIED,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public String File_GetPath(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETPATH,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public int File_GetSize(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETSIZE,
           new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public String File_GetType(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETTYPE,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public Object File_GetValue(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETVALUE,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void File_PutValue(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_PUTVALUE,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public int File_GetAttribute(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETATTRIBUTE,
           new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public String File_GetHelp(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETHELP,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public String File_GetName(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETNAME,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public Object File_GetTag(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETTAG,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void File_PutTag(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_PUTTAG,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public Object File_GetID(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_GETID,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void File_PutID(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_PUTID,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public void File_Release(int iHandle) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.FILE_RELEASE,
           new Object[]{ iHandle }));
        return;
    }

    public int Robot_GetVariable(int iHandle, String strName, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_GETVARIABLE,
            new Object[]{ iHandle, strName, strOption }));
        return (Integer)recv.Args.get(0);
    }

    public Object Robot_GetVariableNames(int iHandle, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_GETVARIABLENAMES,
           new Object[]{ iHandle, strOption }));
        return recv.Args.get(0);
    }

    public Object Robot_Execute(int iHandle, String strCommand, Object objParam) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_EXECUTE,
           new Object[]{ iHandle, strCommand, objParam }));
        return recv.Args.get(0);
    }

    public void Robot_Accelerate(int iHandle, int iAxis, float fAccel, float fDecel) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_ACCELERATE,
           new Object[]{ iHandle, iAxis, fAccel, fDecel }));
        return;
    }

    public void Robot_Change(int iHandle, String strName) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_CHANGE,
           new Object[]{ iHandle, strName }));
        return;
    }

    public void Robot_Chuck(int iHandle, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_CHUCK,
           new Object[]{ iHandle, strOption }));
        return;
    }

    public void Robot_Drive(int iHandle, int iAxis, float fMov, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_DRIVE,
           new Object[]{ iHandle, iAxis, fMov, strOption }));
        return;
    }

    public void Robot_GoHome(int iHandle) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_GOHOME,
           new Object[]{ iHandle }));
        return;
    }

    public void Robot_Halt(int iHandle, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_HALT,
           new Object[]{ iHandle, strOption }));
        return;
    }

    public void Robot_Hold(int iHandle, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_HOLD,
           new Object[]{ iHandle, strOption }));
        return;
    }

    public void Robot_Move(int iHandle, int iComp, Object objPose, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_MOVE,
           new Object[]{ iHandle, iComp, objPose, strOption }));
        return;
    }

    public void Robot_Rotate(int iHandle, Object objRotSuf, float fDeg, Object objPivot, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_ROTATE,
           new Object[]{ iHandle, objRotSuf, fDeg, objPivot, strOption }));
        return;
    }

    public void Robot_Speed(int iHandle, int iAxis, float fSpeed) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_SPEED,
           new Object[]{ iHandle, iAxis, fSpeed }));
        return;
    }

    public void Robot_Unchuck(int iHandle, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_UNCHUCK,
           new Object[]{ iHandle, strOption }));
        return;
    }

    public void Robot_Unhold(int iHandle, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_UNHOLD,
           new Object[]{ iHandle, strOption }));
        return;
    }

    public int Robot_GetAttribute(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_GETATTRIBUTE,
           new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public String Robot_GetHelp(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_GETHELP,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public String Robot_GetName(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_GETNAME,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public Object Robot_GetTag(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_GETTAG,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Robot_PutTag(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_PUTTAG,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public Object Robot_GetID(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_GETID,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Robot_PutID(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_PUTID,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public void Robot_Release(int iHandle) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.ROBOT_RELEASE,
           new Object[]{ iHandle }));
        return;
    }

    public int Task_GetVariable(int iHandle, String strName, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_GETVARIABLE,
            new Object[]{ iHandle, strName, strOption }));
        return (Integer)recv.Args.get(0);
    }

    public Object Task_GetVariableNames(int iHandle, String strOption) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_GETVARIABLENAMES,
           new Object[]{ iHandle, strOption }));
        return recv.Args.get(0);
    }

    public Object Task_Execute(int iHandle, String strCommand, Object objParam) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_EXECUTE,
           new Object[]{ iHandle, strCommand, objParam }));
        return recv.Args.get(0);
    }

    public void Task_Start(int iHandle, int iMode, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_START,
           new Object[]{ iHandle, iMode, strOption }));
        return;
    }

    public void Task_Stop(int iHandle, int iMode, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_STOP,
           new Object[]{ iHandle, iMode, strOption }));
        return;
    }

    public void Task_Delete(int iHandle, String strOption) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_DELETE,
           new Object[]{ iHandle, strOption }));
        return;
    }

    public String Task_GetFileName(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_GETFILENAME,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public int Task_GetAttribute(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_GETATTRIBUTE,
           new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public String Task_GetHelp(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_GETHELP,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public String Task_GetName(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_GETNAME,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public Object Task_GetTag(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_GETTAG,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Task_PutTag(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_PUTTAG,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public Object Task_GetID(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_GETID,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Task_PutID(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_PUTID,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public void Task_Release(int iHandle) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.TASK_RELEASE,
           new Object[]{ iHandle }));
        return;
    }

    public Object Variable_GetDateTime(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.VARIABLE_GETDATETIME,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public Object Variable_GetValue(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.VARIABLE_GETVALUE,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Variable_PutValue(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.VARIABLE_PUTVALUE,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public int Variable_GetAttribute(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.VARIABLE_GETATTRIBUTE,
           new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public String Variable_GetHelp(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.VARIABLE_GETHELP,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public String Variable_GetName(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.VARIABLE_GETNAME,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public Object Variable_GetTag(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.VARIABLE_GETTAG,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Variable_PutTag(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.VARIABLE_PUTTAG,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public Object Variable_GetID(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.VARIABLE_GETID,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Variable_PutID(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.VARIABLE_PUTID,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public int Variable_GetMicrosecond(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.VARIABLE_GETMICROSECOND,
           new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public void Variable_Release(int iHandle) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.VARIABLE_RELEASE,
           new Object[]{ iHandle }));
        return;
    }

    public void Command_Execute(int iHandle, int iMode) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_EXECUTE,
           new Object[]{ iHandle, iMode }));
        return;
    }

    public void Command_Cancel(int iHandle) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_CANCEL,
           new Object[]{ iHandle }));
        return;
    }

    public int Command_GetTimeout(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_GETTIMEOUT,
           new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public void Command_PutTimeout(int iHandle, int newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_PUTTIMEOUT,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public int Command_GetState(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_GETSTATE,
           new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public Object Command_GetParameters(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_GETPARAMETERS,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Command_PutParameters(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_PUTPARAMETERS,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public Object Command_GetResult(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_GETRESULT,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public int Command_GetAttribute(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_GETATTRIBUTE,
           new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public String Command_GetHelp(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_GETHELP,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public String Command_GetName(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_GETNAME,
           new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public Object Command_GetTag(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_GETTAG,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Command_PutTag(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_PUTTAG,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public Object Command_GetID(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_GETID,
           new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Command_PutID(int iHandle, Object newVal) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_PUTID,
           new Object[]{ iHandle, newVal }));
        return;
    }

    public void Command_Release(int iHandle) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.COMMAND_RELEASE,
           new Object[]{ iHandle }));
        return;
    }

    public void Message_Reply(int iHandle, Object objData) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.MESSAGE_REPLY,
           new Object[]{ iHandle, objData }));
        return;
    }

    public void Message_Clear(int iHandle) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.MESSAGE_CLEAR,
           new Object[]{ iHandle }));
        return;
    }

    public Object Message_GetDateTime(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.MESSAGE_GETDATETIME,
            new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public String Message_GetDescription(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.MESSAGE_GETDESCRIPTION,
            new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public String Message_GetDestination(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.MESSAGE_GETDESTINATION,
            new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public int Message_GetNumber(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.MESSAGE_GETNUMBER,
            new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public int Message_GetSerialNumber(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.MESSAGE_GETSERIALNUMBER,
            new Object[]{ iHandle }));
        return (Integer)recv.Args.get(0);
    }

    public String Message_GetSource(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.MESSAGE_GETSOURCE,
            new Object[]{ iHandle }));
        return (String)recv.Args.get(0);
    }

    public Object Message_GetValue(int iHandle) throws Throwable {
        BCAPPacket recv = m_conn.SendRecv(new BCAPPacket(BCAPDefine.MESSAGE_GETVALUE,
            new Object[]{ iHandle }));
        return recv.Args.get(0);
    }

    public void Message_Release(int iHandle) throws Throwable {
        m_conn.SendRecv(new BCAPPacket(BCAPDefine.MESSAGE_RELEASE,
           new Object[]{ iHandle }));
        return;
    }
}
