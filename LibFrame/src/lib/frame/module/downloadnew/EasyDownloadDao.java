package lib.frame.module.downloadnew;

import android.content.Context;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 下载Provider
 * 
 * @author zhangbp
 */

public class EasyDownloadDao {

	private String DbName = "EasyDownLoadDB.db";
	private String tableName = "downLoadInfos";
	private Context mContext;
	private Connection conn;
	private HashMap<String, PreparedStatement> preparedStatementMaps;

	public EasyDownloadDao(Context mContext) {
		this.mContext = mContext.getApplicationContext();
		createTable();
	}

	/**
	 * 读取所以的下载列表
	 * 
	 * @return
	 * @author zhangbp
	 */

	public List<EasyDownloadInfo> getDownloadList() {

		Connection conn = getConnection();
		if (conn != null) {
			try {
				ArrayList<EasyDownloadInfo> results = new ArrayList<>();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from " + tableName);
				while (rs.next()) {
					EasyDownloadInfo mEasyDownloadInfo = new EasyDownloadInfo();
					mEasyDownloadInfo.url = rs.getString("url");
					mEasyDownloadInfo.fileDir = rs.getString("fileDir");
					mEasyDownloadInfo.fileName = rs.getString("fileName");
					mEasyDownloadInfo.totalSize = rs.getLong("totalSize");
					mEasyDownloadInfo.completeSize = rs.getLong("completeSize");
					mEasyDownloadInfo.description = rs.getString("description");
					mEasyDownloadInfo.status = rs.getInt("status");
					mEasyDownloadInfo.createTime = rs.getString("createTime");
					results.add(mEasyDownloadInfo);
				}
				return results;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return null;
	}

	/**
	 * 添加下载信息
	 * 
	 * @param mEasyDownloadInfo
	 * @return
	 */

	public Boolean addDownload(EasyDownloadInfo mEasyDownloadInfo) {
		Boolean status = false;
		Connection conn = getConnection();
		if (conn != null) {
			try {
				PreparedStatement stmt = getPrepareStatement("insert into " + tableName + " (url,fileDir,fileName,totalSize,completeSize,description,status,createTime) values (?,?,?,?,?,?,?,?)");
				downloadInfoAddPre(stmt, mEasyDownloadInfo);
				status = stmt.execute();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return status;
	}

	/**
	 * 修改下载信息
	 * 
	 * @param mEasyDownloadInfo
	 * @return
	 */

	public int updateDownload(EasyDownloadInfo mEasyDownloadInfo) {
		int count = 0;
		Connection conn = getConnection();
		if (conn != null) {
			try {
				PreparedStatement stmt = getPrepareStatement("update " + tableName + " set url = ?,fileDir = ? ,fileName = ?,totalSize = ?,completeSize = ?,description = ?,status = ?,createTime = ? where url = ?");
				downloadInfoAddPre(stmt, mEasyDownloadInfo);
				stmt.setString(9, mEasyDownloadInfo.url);
				count = stmt.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * 修改下载中的状态为停止下载
	 * 
	 * @param mEasyDownloadInfo
	 * @return
	 */

	public int amendmentData() {
		int count = 0;
		Connection conn = getConnection();
		if (conn != null) {
			try {
				PreparedStatement stmt = getPrepareStatement("update " + tableName + " set status = ? where status = ?");
				stmt.setInt(1, EasyDownloadInfo.State.DOWNLOAD_STATE_STOP);
				stmt.setInt(2, EasyDownloadInfo.State.DOWNLOAD_STATE_DOWNLOADING);
				count = stmt.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * 删除下载信息
	 * 
	 * @param mEasyDownloadInfo
	 * @return
	 */

	public Boolean delDownload(String url) {
		Boolean status = false;
		Connection conn = getConnection();
		if (conn != null) {
			try {
				PreparedStatement stmt = getPrepareStatement("delete from " + tableName + " where url = ?");
				stmt.setString(1, url);
				status = stmt.execute();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return status;
	}

	/**
	 * 添加EasyDownloadInfo到PreparedStatement
	 * 
	 * @param stmt
	 * @param mEasyDownloadInfo
	 * @throws SQLException
	 */

	private void downloadInfoAddPre(PreparedStatement stmt, EasyDownloadInfo mEasyDownloadInfo) throws SQLException {
		stmt.setString(1, mEasyDownloadInfo.url);
		stmt.setString(2, mEasyDownloadInfo.fileDir);
		stmt.setString(3, mEasyDownloadInfo.fileName);
		stmt.setLong(4, mEasyDownloadInfo.totalSize);
		stmt.setLong(5, mEasyDownloadInfo.completeSize);
		stmt.setString(6, mEasyDownloadInfo.description);
		stmt.setInt(7, mEasyDownloadInfo.status);
		stmt.setString(8, mEasyDownloadInfo.createTime);
	}

	/**
	 * 创建表
	 * 
	 * @author zhangbp
	 */

	private void createTable() {
		Connection conn = getConnection();
		if (conn != null) {
			try {
				Statement stmt = conn.createStatement();
				stmt.execute("CREATE TABLE IF NOT EXISTS \"" + tableName + "\" (\"id\"  integer,\"url\"  varchar,\"fileDir\"  varchar,\"fileName\"  varchar,\"totalSize\"  varchar,\"completeSize\"  varchar,\"description\"  varchar,\"status\"  varchar,\"createTime\"  varchar,PRIMARY KEY (\"id\" ASC),UNIQUE (\"url\", \"createTime\"))");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * 创建数据库链接
	 * 
	 * @return
	 * @author zhangbp
	 */

	public Connection getConnection() {
		if (conn == null) {
			try {
				File dbPath = mContext.getDatabasePath(DbName);
				File parentDir = dbPath.getParentFile();
				if (!parentDir.exists()) {
					parentDir.mkdirs();
				}
				Class.forName("org.sqldroid.SQLDroidDriver");
				conn = DriverManager.getConnection("jdbc:sqldroid:" + dbPath.getPath());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return conn;
	}

	/**
	 * 关闭Connection
	 */
	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private PreparedStatement getPrepareStatement(String sql) throws SQLException {
		if (conn != null) {
			if (preparedStatementMaps == null) {
				preparedStatementMaps = new HashMap<>();
			}

			PreparedStatement stmt = preparedStatementMaps.get(sql);
			if (stmt == null) {
				stmt = conn.prepareStatement(sql);
				preparedStatementMaps.put(sql, stmt);
			}

			return stmt;
		}
		return null;
	}

}
