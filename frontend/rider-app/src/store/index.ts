import { create } from 'zustand'
import { persist } from 'zustand/middleware'

// 骑手状态
interface RiderInfo {
  id: number
  phone: string
  nickname: string
  avatar: string
  status: 'idle' | 'working' | 'busy'
}

interface RiderState {
  riderInfo: RiderInfo | null
  token: string | null
  
  setRiderInfo: (rider: RiderInfo) => void
  setToken: (token: string) => void
  updateStatus: (status: RiderInfo['status']) => void
  logout: () => void
}

export const useRiderStore = create<RiderState>()(
  persist(
    (set) => ({
      riderInfo: null,
      token: null,

      setRiderInfo: (riderInfo) => set({ riderInfo }),
      setToken: (token) => set({ token }),
      
      updateStatus: (status) => set((state) => ({
        riderInfo: state.riderInfo ? { ...state.riderInfo, status } : null
      })),
      
      logout: () => set({ riderInfo: null, token: null })
    }),
    {
      name: 'rider-storage',
      partialize: (state) => ({
        token: state.token,
        riderInfo: state.riderInfo
      })
    }
  )
)
