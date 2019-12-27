package com.dy.u.file.repository;

import com.dy.u.file.bean.File;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * File 存储库.
 *
 * @author dxy
 */
public interface FileRepository extends MongoRepository<File, String> {


}
