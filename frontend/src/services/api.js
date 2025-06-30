// API 기본 설정
const API_CONFIG = {
  author: process.env.REACT_APP_AUTHOR_API_URL || 'http://localhost:8083',
  customer: process.env.REACT_APP_CUSTOMER_API_URL || 'http://localhost:8082',
  platform: process.env.REACT_APP_PLATFORM_API_URL || 'http://localhost:8081',
  gateway: process.env.REACT_APP_GATEWAY_URL || 'http://localhost:8080'
};

/**
 * HTTP 요청을 처리하는 기본 fetch 래퍼
 * @param {string} url - 요청 URL
 * @param {object} options - fetch 옵션
 * @returns {Promise} 응답 데이터
 */
const apiRequest = async (url, options = {}) => {
  const defaultOptions = {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  };

  try {
    console.log(`API 요청: ${options.method || 'GET'} ${url}`);
    
    const response = await fetch(url, defaultOptions);
    
    console.log(`API 응답: ${response.status} ${response.statusText}`);

    // 응답 처리
    if (!response.ok) {
      const errorText = await response.text();
      console.error('API 오류 응답:', errorText);
      
      throw new Error(
        response.status === 404 
          ? '요청한 리소스를 찾을 수 없습니다.'
          : response.status === 500
          ? '서버 내부 오류가 발생했습니다.'
          : response.status === 400
          ? errorText || '잘못된 요청입니다.'
          : `HTTP ${response.status}: ${response.statusText}`
      );
    }

    // JSON 응답 파싱
    const data = await response.json();
    console.log('API 응답 데이터:', data);
    
    return data;
  } catch (error) {
    console.error('API 요청 실패:', error);
    
    // 네트워크 오류 처리
    if (error.name === 'TypeError' && error.message.includes('fetch')) {
      throw new Error('서버에 연결할 수 없습니다. 네트워크 연결을 확인해주세요.');
    }
    
    throw error;
  }
};

/**
 * Author API 서비스
 */
export const authorAPI = {
  /**
   * 작가 등록
   * @param {object} authorData - 작가 등록 데이터
   * @param {string} authorData.email - 이메일
   * @param {string} authorData.name - 이름
   * @param {string} authorData.introduction - 소개글
   * @returns {Promise} 등록된 작가 정보
   */
  registerAuthor: async (authorData) => {
    const url = `${API_CONFIG.author}/authors/registerauthor`;
    
    const payload = {
      email: authorData.email,
      name: authorData.name,
      introduction: authorData.introduction || ''
    };

    return await apiRequest(url, {
      method: 'POST',
      body: JSON.stringify(payload),
    });
  },

  /**
   * 작가 목록 조회
   * @returns {Promise} 작가 목록
   */
  getAuthors: async () => {
    const url = `${API_CONFIG.author}/authors`;
    return await apiRequest(url);
  },

  /**
   * 특정 작가 조회
   * @param {number} authorId - 작가 ID
   * @returns {Promise} 작가 정보
   */
  getAuthor: async (authorId) => {
    const url = `${API_CONFIG.author}/authors/${authorId}`;
    return await apiRequest(url);
  },

  /**
   * 이메일로 작가 검색
   * @param {string} email - 이메일
   * @returns {Promise} 작가 정보
   */
  getAuthorByEmail: async (email) => {
    const url = `${API_CONFIG.author}/authors/search/findByEmail?email=${encodeURIComponent(email)}`;
    return await apiRequest(url);
  },

  /**
   * 작가 승인
   * @param {number} authorId - 작가 ID
   * @returns {Promise} 승인된 작가 정보
   */
  approveAuthor: async (authorId) => {
    const url = `${API_CONFIG.author}/authors/${authorId}/approveauthor`;
    
    return await apiRequest(url, {
      method: 'PUT',
      body: JSON.stringify({ isApproved: true }),
    });
  },

  /**
   * 작가 승인 거부
   * @param {number} authorId - 작가 ID
   * @returns {Promise} 거부된 작가 정보
   */
  disapproveAuthor: async (authorId) => {
    const url = `${API_CONFIG.author}/authors/${authorId}/disapproveauthor`;
    
    return await apiRequest(url, {
      method: 'PUT',
      body: JSON.stringify({ isApproved: false }),
    });
  },

  /**
   * 작가 정보 수정
   * @param {number} authorId - 작가 ID
   * @param {object} updateData - 수정할 데이터
   * @param {string} updateData.email - 새 이메일 (선택)
   * @param {string} updateData.introduction - 새 소개글 (선택)
   * @returns {Promise} 수정된 작가 정보
   */
  editAuthor: async (authorId, updateData) => {
    const url = `${API_CONFIG.author}/authors/${authorId}/editauthor`;
    
    const payload = {};
    if (updateData.email) payload.email = updateData.email;
    if (updateData.introduction !== undefined) payload.introduction = updateData.introduction;
    
    return await apiRequest(url, {
      method: 'PUT',
      body: JSON.stringify(payload),
    });
  }
};

/**
 * Customer API 서비스 (추후 확장용)
 */
export const customerAPI = {
  // TODO: Customer API 메소드들 구현
};

/**
 * Platform API 서비스 (추후 확장용)
 */
export const platformAPI = {
  // TODO: Platform API 메소드들 구현
};

export default {
  authorAPI,
  customerAPI,
  platformAPI
};
