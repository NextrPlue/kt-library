import React, { useEffect, useState } from 'react';

const CustomerPoints = () => {
  const [point, setPoint] = useState(null);
  const [loading, setLoading] = useState(true);
  const [amount, setAmount] = useState(0); // 적립 금액 입력

  // 포인트 조회
  const fetchPoint = async () => {
    try {
      const userJson = localStorage.getItem('user');
      if (!userJson) throw new Error('로그인 정보가 없습니다.');

      const user = JSON.parse(userJson);
      const response = await fetch(
        `https://8082-baesj1-ktlibrary-w3b1w8wc93i.ws-us120.gitpod.io/points/${user.id}`
      );
      const data = await response.json();
      setPoint(data.point);
    } catch (error) {
      console.error('포인트 조회 실패:', error.message);
      setPoint(undefined); // 명시적으로 에러 처리
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPoint();
  }, []);

  // 포인트 적립 처리
  const handleRecharge = async () => {
    if (amount <= 0) return alert("1P 이상 입력해주세요!");

    try {
      const userJson = localStorage.getItem('user');
      if (!userJson) throw new Error('로그인 필요');

      const user = JSON.parse(userJson);
      const response = await fetch(
        `https://8082-baesj1-ktlibrary-w3b1w8wc93i.ws-us120.gitpod.io/points/requestpoint`,
        {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ customerId: user.id, point: amount }),
        }
      );

      if (response.ok) {
        alert(`${amount.toLocaleString()}P 적립되었습니다!`);
        setAmount(0); // 초기화
        fetchPoint(); // 새로고침
      } else {
        alert('적립 실패');
      }
    } catch (err) {
      console.error('적립 오류:', err.message);
    }
  };

  // 빠른 금액 추가
  const addAmount = (value) => {
    setAmount((prev) => prev + value);
  };

  return (
    <div
      style={{
        minHeight: '100vh',
        backgroundColor: '#f9fafb',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        padding: '2rem',
      }}
    >
      <div
        style={{
          backgroundColor: 'white',
          padding: '2rem',
          borderRadius: '1rem',
          boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
          width: '100%',
          maxWidth: '500px',
          textAlign: 'center',
        }}
      >
        <h1 style={{ fontSize: '1.75rem', fontWeight: 'bold', color: '#1f2937' }}>포인트</h1>

        {/* 안전한 조건부 렌더링 */}
        {loading ? (
          <p style={{ marginTop: '1rem' }}>불러오는 중...</p>
        ) : typeof point === 'number' ? (
          <p style={{ fontSize: '2rem', fontWeight: 'bold', color: '#2563eb', marginTop: '1rem' }}>
            {point.toLocaleString()}P
          </p>
        ) : (
          <p style={{ marginTop: '1rem', color: '#dc2626' }}>포인트 정보를 불러올 수 없습니다.</p>
        )}

        {/* 입력창 */}
        <div style={{ marginTop: '2rem' }}>
          <input
            type="number"
            value={amount === 0 ? '' : amount}
            onChange={(e) => {
              const raw = e.target.value;
              const cleaned = raw.replace(/^0+/, '');
              setAmount(Number(cleaned));
            }}
            placeholder="구매할 포인트 입력"
            style={{
              width: '100%',
              padding: '0.75rem',
              fontSize: '1rem',
              borderRadius: '0.5rem',
              border: '1px solid #d1d5db',
              marginBottom: '1rem',
              textAlign: 'center',
            }}
          />

          {/* 빠른 버튼 */}
          <div style={{ display: 'flex', justifyContent: 'center', gap: '0.5rem', marginBottom: '1.5rem' }}>
            {[1000, 3000, 5000].map((v) => (
              <button
                key={v}
                onClick={() => addAmount(v)}
                style={{
                  padding: '0.5rem 1rem',
                  backgroundColor: '#e0f2fe',
                  borderRadius: '0.5rem',
                  border: '1px solid #60a5fa',
                  color: '#1d4ed8',
                  cursor: 'pointer',
                  fontWeight: 'bold',
                }}
              >
                +{v.toLocaleString()}P
              </button>
            ))}
          </div>

          {/* 구매 버튼 */}
          <button
            onClick={handleRecharge}
            style={{
              padding: '0.75rem 1.5rem',
              fontSize: '1rem',
              backgroundColor: '#2563eb',
              color: 'white',
              border: 'none',
              borderRadius: '8px',
              cursor: 'pointer',
            }}
          >
            포인트 구매하기
          </button>
        </div>
      </div>
    </div>
  );
};

export default CustomerPoints;
