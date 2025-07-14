import { writable } from 'svelte/store';
import { signOut as serverSignOut } from '$lib/services/userAuthenticationService';

const isBrowser = typeof window !== 'undefined';

interface AuthState {
    token: string | null;
    loading: boolean;
}

function createAuthStore() {
    const { subscribe, set, update } = writable<AuthState>({
        token: null,
        loading: true
    });

    if (isBrowser) {
        const storedToken = localStorage.getItem('token');
        set({ token: storedToken, loading: false });
    }

    return {
        subscribe,
        signIn: (token: string) => {
            if (isBrowser) localStorage.setItem('token', token);
            set({ token, loading: false });
        },
        signOut: async () => {
            if (isBrowser) {
                await serverSignOut();
                localStorage.removeItem('token');
            }
            set({ token: null, loading: false });
        },
        setLoading: (loading: boolean) =>
            update((state) => ({ ...state, loading }))
    };
}

export const auth = createAuthStore();