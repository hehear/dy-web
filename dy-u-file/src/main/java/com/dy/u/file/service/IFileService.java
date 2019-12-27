package com.dy.u.file.service;
import java.util.List;
import java.util.Optional;

import com.dy.u.file.bean.File;

/**
 * File 服务接口.
 *
 * @author dxy
 */
public interface IFileService {
	/**
	 * 保存文件
	 * @param
	 * @return
	 */
	File saveFile(File file);

	/**
	 * 删除文件
	 * @param
	 * @return
	 */
	void removeFile(String id);

	/**
	 * 根据id获取文件
	 * @param
	 * @return
	 */
	Optional<File> getFileById(String id);

	/**
	 * 分页查询，按上传时间降序
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<File> listFilesByPage(int pageIndex, int pageSize);
}
