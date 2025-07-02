import React, { useState, useEffect } from 'react';
import axios from 'axios';
import BookCard from '../components/BookCard';
import styles from '../styles/CustomerBooks.module.css';

const CustomerBooks = () => {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedCategory, setSelectedCategory] = useState('전체');

  // 백엔드 API URL
  const API_BASE_URL = process.env.REACT_APP_API_URL || 'https://8087-sorasol9-ktlibrary-00lz4rdx3ja.ws-us120.gitpod.io';

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`${API_BASE_URL}/bookShelves/all`);
      setBooks(response.data);
      setError(null);
    } catch (err) {
      console.error('도서 목록을 불러오는데 실패했습니다:', err);
      setError('도서 목록을 불러오는데 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };

  // 도서 열람 (클릭 시)
  const handleBookClick = async (book) => {
    try {
      // 열람 API 호출
      await axios.post(`${API_BASE_URL}/bookShelves/${book.id}/read`);
      
      // 열람 후 도서 정보 새로고침
      fetchBooks();
      
      // 여기에 도서 상세 페이지로 이동하거나 모달을 띄우는 로직 추가 가능
      alert(`"${book.title}" 도서를 열람했습니다.`);
    } catch (err) {
      console.error('도서 열람 실패:', err);
      alert('도서 열람에 실패했습니다.');
    }
  };

  // 카테고리별 필터링
  const filteredBooks = selectedCategory === '전체' 
    ? books 
    : books.filter(book => book.category === selectedCategory);

  // 카테고리 목록 추출
  const categories = ['전체', ...new Set(books.map(book => book.category))];

  if (loading) {
    return (
      <div className={styles.container}>
        <div className={styles.loading}>
          <span className="material-icons">hourglass_empty</span>
          <p>도서 목록을 불러오는 중...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className={styles.container}>
        <div className={styles.error}>
          <span className="material-icons">error_outline</span>
          <p>{error}</p>
          <button onClick={fetchBooks} className={styles.retryButton}>
            다시 시도
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h1>도서 둘러보기</h1>
        <p>다양한 도서를 둘러보고 원하는 책을 찾아보세요</p>
      </div>

      {/* 카테고리 필터 */}
      <div className={styles.categoryFilter}>
        {categories.map(category => (
          <button
            key={category}
            className={`${styles.categoryBtn} ${selectedCategory === category ? styles.active : ''}`}
            onClick={() => setSelectedCategory(category)}
          >
            {category}
          </button>
        ))}
      </div>

      {/* 도서 개수 표시 */}
      <div className={styles.bookCount}>
        <span>전체 {filteredBooks.length}권</span>
      </div>

      {/* 도서 목록 */}
      {filteredBooks.length === 0 ? (
        <div className={styles.noBooks}>
          <span className="material-icons">library_books</span>
          <p>등록된 도서가 없습니다.</p>
        </div>
      ) : (
        <div className={styles.booksGrid}>
          {filteredBooks.map(book => (
            <BookCard 
              key={book.id} 
              book={book} 
              onClick={handleBookClick}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default CustomerBooks;