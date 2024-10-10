package com.ktdsuniversity.edu.hello_spring.bbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.hello_spring.bbs.dao.BoardDao;
import com.ktdsuniversity.edu.hello_spring.bbs.service.BoardService;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.BoardListVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.BoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ModifyBoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.WriteBoardVO;
import com.ktdsuniversity.edu.hello_spring.common.beans.FileHandler;
import com.ktdsuniversity.edu.hello_spring.common.vo.StoreResultVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	// application.yml 파일에서 "app.multipart.base-dir" 설정 값을 가져온다
	// @Value는 Spring Bean에서만 사용이 가능
	// Spring Bean: @Controller, @Service, Repository
	//	@Value("${app.multipart.base-dir}")
	//	private String baseDirectory;

	@Autowired
	private FileHandler fileHandler;
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public BoardListVO getAllBoard() {
		// 게시글 목록 화면에 데이터를 전송해주기 위해서 게시글의 건수와 게시글의 목록을 조회해 반환시킨다
		
		// 1. 게시글의 건수를 조회한다.
		int boardCount = this.boardDao.selectBoardAllCount();
		
		// 2. 게시글의 목록을 조회한다.
		List<BoardVO> boardList = this.boardDao.selectAllBoard();
		
		// 3. BoardListVO를 만들어서 게시글의 건수와 목록을 할당한다.
		BoardListVO boardListVO = new BoardListVO();
		boardListVO.setBoardCnt(boardCount);
		boardListVO.setBoardList(boardList);
		
		// 4. BoardListVO 인스턴스를 반환한다.
		return boardListVO;
	}
	
	@Override
	public boolean createNewBoard(WriteBoardVO writeBoardVO) {
		
		// 파일 업로드 처리
		MultipartFile file = writeBoardVO.getFile();
		
		StoreResultVO storeResultVO = this.fileHandler.storeFile(file);
		if(storeResultVO != null) {
			writeBoardVO.setFileName(storeResultVO.getObfuscatedFileName());
			writeBoardVO.setOriginFileName(storeResultVO.getOriginFilename());
		}
		
		int numberCreation = this.boardDao.inserNewBoard(writeBoardVO);
		return numberCreation > 0;
	}

	@Override
	public BoardVO selectOneBoard(int id, boolean isIncrease) {
		if(isIncrease) {
			int updateCount = boardDao.updateViewCount(id);
			if(updateCount == 0) {
				throw new IllegalArgumentException("잘못된 정보입니다");
			}
		}
		BoardVO boardVO = boardDao.selectOneBoard(id);
		if(boardVO == null) {
			throw new IllegalArgumentException("잘못된 접근입니다");
		}
		return boardVO;
	}
	
	@Override
	public boolean updateOneBoard(ModifyBoardVO modifyBoardVO) {
		
		// 기존의 파일을 삭제하기 위해서 업데이트 하기 전 게시글의 정보를 조회한다
		BoardVO boardVO = this.boardDao.selectOneBoard(modifyBoardVO.getId());
		
		
		MultipartFile file = modifyBoardVO.getFile();
		
		StoreResultVO storeResultVO = this.fileHandler.storeFile(file);
		if(storeResultVO != null) {
			modifyBoardVO.setFileName(storeResultVO.getObfuscatedFileName());
			modifyBoardVO.setOriginFileName(storeResultVO.getOriginFilename());
		}
		
		int updateCount = boardDao.updateOneBoard(modifyBoardVO);
		
		if(updateCount > 0) {
			this.fileHandler.deleteFile(boardVO.getFileName());
		}
		
		return updateCount > 0;
	}
	
	@Override
	public boolean deleteOneBoard(int id) {
		// 기존의 파일을 삭제하기 위해서 업데이트 하기 전 게시글의 정보를 조회한다
		BoardVO boardVO = this.boardDao.selectOneBoard(id);
		
		int deleteCount = boardDao.deleteOneBoard(id);
		
		if(deleteCount > 0) {
			this.fileHandler.deleteFile(boardVO.getFileName());
		}
		return deleteCount > 0;
	}
}
