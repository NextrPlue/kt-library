// src/pages/ManuscriptRegister.js
import React, { useState } from 'react';
import { manuscriptAPI } from '../services/api';

const ManuscriptRegister = () => {
  const user = JSON.parse(localStorage.getItem('user'));
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await manuscriptAPI.registerManuscript({
        authorId: user.id,
        manuscriptTitle: title,
        manuscriptContent: content,
      });
      alert('원고 등록 성공!');
      setTitle('');
      setContent('');
    } catch (err) {
      alert(`등록 실패: ${err.message}`);
    }
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h1>새 원고</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>제목</label><br />
          <input
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
            style={{ width: '100%', padding: '0.5rem' }}
          />
        </div>
        <div style={{ marginTop: '1rem' }}>
          <label>내용</label><br />
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
            rows={10}
            style={{ width: '100%', padding: '0.5rem' }}
          />
        </div>
        <button type="submit" style={{ marginTop: '1rem', padding: '0.5rem 1rem' }}>
          등록
        </button>
      </form>
    </div>
  );
};

export default ManuscriptRegister;
