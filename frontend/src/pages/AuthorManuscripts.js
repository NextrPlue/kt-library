// src/pages/AuthorManuscripts.js
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { manuscriptAPI } from '../services/api';
import styles from '../styles/AuthorManuscripts.module.css';

const AuthorManuscripts = () => {
  const [manuscripts, setManuscripts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) return;

    const fetchData = async () => {
      try {
        const res = await manuscriptAPI.getAllManuscripts();
        const all = res._embedded?.manuscripts || [];

        const myList = all.filter(m => String(m.authorId) === String(user.id));
        setManuscripts(myList);
      } catch (err) {
        console.error('원고 목록 조회 실패:', err.message);
      }
    };

    fetchData();
  }, []);

  const handleEdit = (manuscript) => {
    navigate('/author/new-manuscript', { state: { manuscript, isEdit: true } });
  };

  const handlePublish = async (manuscript) => {
    console.log('📦 출판 요청 직전 데이터:', manuscript);  // ✅ 여기에 추가!

    try {
      await manuscriptAPI.requestPublishing({
        manuscriptId: manuscript.id,
        authorName: manuscript.authorName || '알 수 없음',
        authorIntroduction: manuscript.authorIntroduction || '',
      });
      alert('출판 요청 완료!');
    } catch (err) {
      alert('출판 요청 실패: ' + err.message);
    }
  };

  return (
    <div className={styles.container}>
      <h1>내 원고</h1>
      {manuscripts.length === 0 ? (
        <p>작성한 원고가 없습니다.</p>
      ) : (
        <div className={styles.cardGrid}>
          {manuscripts.map((m) => (
            <div key={m.id} className={styles.card}>
              <h2 className={styles.title}>{m.manuscriptTitle}</h2>
              <p className={styles.content}>{m.manuscriptContent}</p>
              <p className={styles.author}>작성자 ID: {m.authorId}</p>
              <div className={styles.actions}>
                <button onClick={() => handleEdit(m)}>수정</button>
                <button onClick={() => handlePublish(m)}>출판 요청</button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default AuthorManuscripts;
