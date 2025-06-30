import React from 'react';
import styles from './BookCard.module.css';

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
    <div className={styles.bookCard} onClick={() => onClick && onClick(book)}>
      {/* 표지 이미지 */}
      <div className={styles.bookCover}>
        {coverUrl ? (
          <img src={coverUrl} alt={title} className={styles.coverImage} />
        ) : (
          <div className={styles.coverPlaceholder}>
            <span className="material-icons">menu_book</span>
          </div>
        )}
        
        {/* 베스트셀러 뱃지 */}
        {isBestSeller && (
          <div className={styles.bestsellerBadge}>
            <span className="material-icons">star</span>
            <span>베스트</span>
          </div>
        )}
        
        {/* 카테고리 태그 */}
        {category && (
          <div className={styles.categoryTag}>
            {category}
          </div>
        )}
      </div>

      {/* 도서 정보 */}
      <div className={styles.bookInfo}>
        <h3 className={styles.bookTitle}>{title}</h3>
        <p className={styles.bookAuthor}>
          <span className="material-icons">person</span>
          {authorName}
        </p>
        
        {summary && (
          <p className={styles.bookSummary}>
            {summary.length > 80 ? `${summary.substring(0, 80)}...` : summary}
          </p>
        )}
        
        <div className={styles.bookMeta}>
          <div className={styles.bookStats}>
            <span className={styles.viewCount}>
              <span className="material-icons">visibility</span>
              {formatViewCount(viewCount)}
            </span>
          </div>
          
          <div className={styles.bookPrice}>
            {formatPrice(price)}
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookCard;