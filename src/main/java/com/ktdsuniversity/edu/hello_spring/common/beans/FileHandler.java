package com.ktdsuniversity.edu.hello_spring.common.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.hello_spring.common.vo.StoreResultVO;

/**
 * @Component => Spring Bean으로 생성해라!
 */
@Component
public class FileHandler {
	
	@Value("${app.multipart.base-dir}")
	private String baseDirectory;
	
	@Value("${app.multipart.obfuscation.enable}")
	private boolean enableObfuscation;
	
	@Value("${app.multipart.obfuscation.hide-ext.enable}")
	private boolean enableHideExtention;
	
	public StoreResultVO storeFile(MultipartFile multipartFile) {
		// 1. 클라이언트가 파일을 전송했는지 확인.
		// 2. 클라이언트가 파일을 전송했다면
		if(multipartFile != null && !multipartFile.isEmpty()) {
			// 3. 파일을 서버 컴퓨터의 특정 폴더로 저장시킨다.
			String obfuscatedFileName = multipartFile.getOriginalFilename();
			
			if(this.enableObfuscation) {
				
				String ext = obfuscatedFileName.substring(obfuscatedFileName.lastIndexOf("."));
				
				obfuscatedFileName = UUID.randomUUID().toString();
				
				if(!this.enableHideExtention) {
					obfuscatedFileName += ext;
				}
			}
			
			File uploadFile = new File(this.baseDirectory, obfuscatedFileName);
			// getOriginalFilename 사용자가 보내준 파일의 이름과 확장자가 들어있음.
			// c 드라이브의 uploadfiles이라는 폴더에 보내줌.
			if(!uploadFile.getParentFile().exists()) {
				uploadFile.getParentFile().mkdirs();
			}
			// uploadfiles가 없으면 생성해주는 코드
			try {
				multipartFile.transferTo(uploadFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				throw new RuntimeException("예기치 못한 이유로 업로드에 실패했습니다. 잠시 후 다시 시도해주세요.");
			}
			// 쓰고 있는 디스크의 용량이 꽉찰 때 IOException 발생 혹은 uploadfiles라는 폴더가 없을 때 발생 혹은 허가지되 않은 경로.
			// 그러나 파일을 생성하기 때문에 폴더 없는 경우는 발생하지 않음.
			// 일반적으로 윈도우는 C 드라이브 막아놓음.
			// 보통 이런 식은 checkedExpcetion 보통 할 수 있는게 없기 때문에 예외를 만들어서 던져버림.
			
			return new StoreResultVO(multipartFile.getOriginalFilename(), obfuscatedFileName);
		}
		
		return null;
	}
	
	public ResponseEntity<Resource> downloadFile(String fileName, String originFileName) {
		// 2. 다운로드 할 파일의 정보를 가지고 있는 File 인스턴스를 생성한다.
		File downloadFile = new File(this.baseDirectory, fileName);
		
		// 3. HTTP Header에 파일 다운로드 정보를 설정한다.
		HttpHeaders header = new HttpHeaders();
		
		// 다운로드 시킬 파일 이름의 인코딩을 변경한다
		try {
			originFileName = new String(originFileName.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
			// HTTP Response에 파일을 첨부해서 보낼건데 파일의 이름은 "~~~~"이야.
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + originFileName);
				
		// 4. 브라우저에게 파일을 전송한다.
		InputStreamResource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(downloadFile));
		} catch (FileNotFoundException e) { // 내가 지금 다운로드 할 파일을 못 찾겠다
			e.printStackTrace();
			throw new IllegalArgumentException("파일이 존재하지 않습니다");
		}
				
		// 브라우저에게 보낼 응답 데이터를 생성한다.
		return ResponseEntity.ok()
				// 응답데이터에 http header 정보를 셋팅한다 (파일 다운로드 정보)
						.headers(header)
						// 다운로드시킬 파일의 크기를 전달한다 - 브라우저가 파일 다운로드 진행 상태를 관리하기 위해서
						.contentLength(downloadFile.length())
						// 다운로드 시킬 파일의 타입을 지정한다
						// 보통 png 파일이라면 image/png 이렇게 지정하는데
						// 타입과 관계없이 강제 다운로드 시키려면 "application/octet-stream"을 이용한다.
						.contentType(MediaType.parseMediaType("application/octet-stream"))
						// 파일을 응답데이터에 전달한다.
						.body(resource);
	}

	public void deleteFile(String fileName) {
		if(fileName == null) {
			return;
		}
		
		File file = new File(this.baseDirectory, fileName);
		
		if(file.exists() && file.isFile()) {
			file.delete();
		}
	}
}
