package service;

import common.DbConnection;
import model.Board;
import model.Post;
import model.Review;
import model.User;
import model.dao.BoardDao;
import model.dao.ReviewDao;
import model.dao.UserDao;

import java.util.List;

public class BoardService {
    private BoardDao boardDao;
    private ReviewDao reviewDao;

    public BoardService() {
        this.boardDao = new BoardDao(DbConnection.getConnection());
        this.reviewDao = new ReviewDao(DbConnection.getConnection());
    }

    public void write(Board board) {
        this.boardDao.insertBoard(board);
    }
    public Post getContent(int idx) {
        return this.boardDao.selectBoardByIdx(idx);
    }
    public List<Post> getPost() {
        return this.boardDao.selectBoard();
    }
    public List<Review> getReview(int board_idx) {
        return this.reviewDao.selectReview(board_idx);
    }
    public void insertReview(Review review) {
        this.reviewDao.insertReview(review);
    }

}
