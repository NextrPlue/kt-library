import React from 'react';
import './BookCard.css';

const BookCard = ({ book, onClick }) => {
  const {
    id,
    title,
    authorName,
    category,
    summary,
    coverUrl,
    price,
    viewCount,
    isBestSeller,
    fileUrl
  } = book;

  const formatPrice = (price) => {
    if (price === 0) return '무료';
    return `${price.toLocaleString()}원`;
  };

  const formatViewCount = (count) => {
    if (count >= 1000) {
      return `${(count / 1000).toFixed(1)}k`;
    }
    return count.toString();
  };

  return (
    <div className="book-card" onClick={() => onClick && onClick(book)}>
      {/* 표지 이미지 */}
      <div className="book-cover">
        {coverUrl ? (
          <img src={coverUrl} alt={title} className="cover-image" />
        ) : (
          <div className="cover-placeholder">
            <span className="material-icons">menu_book</span>
          </div>
        )}
        
        {/* 베스트셀러 뱃지 */}
        {isBestSeller && (
          <div className="bestseller-badge">
            <span className="material-icons">star</span>
            <span>베스트</span>
          </div>
        )}
        
        {/* 카테고리 태그 */}
        {category && (
          <div className="category-tag">
            {category}
          </div>
        )}
      </div>

      {/* 도서 정보 */}
      <div className="book-info">
        <h3 className="book-title">{title}</h3>
        <p className="book-author">
          <span className="material-icons">person</span>
          {authorName}
        </p>
        
        {summary && (
          <p className="book-summary">
            {summary.length > 80 ? `${summary.substring(0, 80)}...` : summary}
          </p>
        )}
        
        <div className="book-meta">
          <div className="book-stats">
            <span className="view-count">
              <span className="material-icons">visibility</span>
              {formatViewCount(viewCount)}
            </span>
          </div>
          
          <div className="book-price">
            {formatPrice(price)}
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookCard;