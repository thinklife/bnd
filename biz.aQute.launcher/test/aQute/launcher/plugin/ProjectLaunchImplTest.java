package aQute.launcher.plugin;

import junit.framework.*;
import aQute.bnd.build.*;
import aQute.lib.io.*;

public class ProjectLaunchImplTest extends TestCase {

	private Workspace	ws;

	protected void setUp() throws Exception {
		ws = new Workspace(IO.getFile("test/ws"));
	}

	protected void tearDown() throws Exception {
		for (Project project : ws.getAllProjects()) {
			project.clean();
		}
		IO.delete(ws.getFile("cnf/cache"));
	}

	public void testParseSystemCapabilities() throws Exception {
		Project project = ws.getProject("p1");
		project.prepare();
		String systemCaps = null;

		try {
			ProjectLauncherImpl launcher = new ProjectLauncherImpl(project);
			launcher.prepare();

			systemCaps = launcher.getSystemCapabilities();
		}
		finally {
			project.close();
			ws.close();
		}
		assertEquals(
				"osgi.native;osgi.native.osname:List<String>=\"Win7,Windows7,Windows 7\";osgi.native.osversion:Version=6.1",
				systemCaps);
	}

	public void testCwdIsProjectBase() throws Exception {
		Project project = ws.getProject("p1");
		project.prepare();
		assertEquals(project.getBase(), new ProjectLauncherImpl(project).getCwd());
	}
}
