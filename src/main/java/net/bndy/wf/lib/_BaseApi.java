/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.bndy.wf.config.ApplicationConfig;
import net.bndy.wf.exceptions.DisabledFeatureException;
import net.bndy.wf.exceptions.NoResourceFoundException;
import net.bndy.wf.exceptions.ResourceIntegrityException;
import net.bndy.wf.modules.core.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;

import net.bndy.lib.*;
import net.bndy.wf.*;
import net.bndy.wf.modules.core.models.User;

public abstract class _BaseApi<T extends _BaseEntity> {

	@Autowired
	private _BaseService<T> service;
	@Autowired
	private FileService fileService;
	@Autowired
    protected ApplicationConfig appliationConfig;

	public User getCurrentUser() {
		return ApplicationContext.getCurrentUser();
	}

	@ApiOperation(value = "Get entity list")
	@RequestMapping(method = RequestMethod.GET)
	public List<T> get() {
		return this.service.getAll();
	}

	@ApiOperation(value = "Get the page model which contains entity list")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Page<T> get(
			@PageableDefault(value = 10, sort = { "lastUpdate" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return this.service.findAll(pageable);
	}

	@ApiOperation(value = "Get entity by id")
	@RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
	public T get(@PathVariable(name = "id") long id) throws NoResourceFoundException {
		T t = this.service.get(id);
		if (t == null) {
			throw new NoResourceFoundException();
		}
		return t;
	}

	@ApiOperation(value = "Update an existing entity")
	@RequestMapping(value = "/{id:\\d+}", method = RequestMethod.PUT)
	public T put(@PathVariable(name = "id") long id, @RequestBody T entity) {
		entity.setId(id);
		return this.service.save(entity);
	}

	@ApiOperation(value = "Add a new entity")
	@RequestMapping(method = RequestMethod.POST)
	public T post(@RequestBody T entity) {
		return this.service.save(entity);
	}

	@ApiOperation(value = "Delete an entity")
	@RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
	public void delete(@PathVariable(name = "id") long id) throws NoResourceFoundException, ResourceIntegrityException {
		this.service.delete(id);
	}

	@ApiOperation(value = "Upload files")
	@RequestMapping(value = "/upload", method = RequestMethod.POST, headers = ("content-type=multipart/*"), consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public net.bndy.wf.modules.core.models.File upload(@RequestPart(required = true) MultipartFile file, HttpServletRequest request)
		throws IllegalStateException, IOException, DisabledFeatureException {

		if (this.appliationConfig.isUploadDisabled()) {
			throw new DisabledFeatureException();
		}

		net.bndy.wf.modules.core.models.File f = new net.bndy.wf.modules.core.models.File();
		f.setSize(file.getSize());
		f.setType(FileHelper.getTypeByName(file.getOriginalFilename()));
		f.setExtName("");

		if (file.getOriginalFilename().indexOf(".") >= 0) {
			f.setExtName(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).replace(".", ""));
			f.setName(file.getOriginalFilename().replace("." + f.getExtName(), ""));
		}

		f.setPath(new SimpleDateFormat("yyyy-MM").format(new Date()));
		if (this.appliationConfig.isRenameUploadFile()) {
			if (f.getExtName() != null && f.getExtName() != "") {
				f.setPath(Paths.get(f.getPath(), f.getUuid() + "." + f.getExtName()).toString());
			} else {
				f.setPath(Paths.get(f.getPath(), f.getUuid()).toString());
			}
		} else {
			if (f.getExtName() != null && f.getExtName() != "") {
				f.setPath(Paths.get(f.getPath(),
					StringHelper.insertBefore(file.getOriginalFilename(),
						file.getOriginalFilename().lastIndexOf("."),
						"_" + StringHelper.generateRandomString(5))
				).toString());
			} else {
				f.setPath(Paths.get(f.getPath(),
					f.getName() + "_" + StringHelper.generateRandomString(5)
				).toString());
			}
		}

		String destAbsPath = Paths.get(this.appliationConfig.getUploadPath(), f.getPath()).toAbsolutePath().toString();

		File destAbsFile = new File(destAbsPath);
		if (!destAbsFile.getParentFile().exists()) {
			destAbsFile.getParentFile().mkdirs();
		}
		InputStream in = file.getInputStream();
		OutputStream out = new FileOutputStream(new File(destAbsPath));
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = in.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();

		f.setPath("/" + f.getPath().replaceAll("[/\\\\]+", "/"));
		f = this.fileService.save(f);

		return f;
	}
}
