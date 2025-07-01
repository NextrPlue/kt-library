import React, { useState } from 'react';
import styles from '../styles/SubscriptionPage.module.css';

const SubscriptionPage = () => {
  const [message, setMessage] = useState('');

  const handlePurchase = () => {
    setMessage('구독권 구매 기능은 아직 구현되지 않았습니다.');
  };

  const handleCancel = () => {
    setMessage('구독권 취소 기능은 아직 구현되지 않았습니다.');
  };

  return (
    <div className={styles.subscriptionContainer}>
      <div className={styles.subscriptionCard}>
        <h1 className={styles.title}>📚 구독 관리</h1>

        <div className={styles.userInfo}>
          <p><strong>사용자:</strong> 홍길동</p>
          <p><strong>이메일:</strong> hong@kt.com</p>
          <p><strong>현재 상태:</strong> 구독 중</p>
        </div>

        <div className={styles.buttonGroup}>
          <button className={styles.purchaseButton} onClick={handlePurchase}>
            구독권 구매
          </button>
          <button className={styles.cancelButton} onClick={handleCancel}>
            구독권 취소
          </button>
        </div>

        {message && <div className={styles.message}>{message}</div>}
      </div>
    </div>
  );
};

export default SubscriptionPage;
