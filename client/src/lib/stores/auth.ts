import { writable } from 'svelte/store';

const isBrowser = typeof window !== 'undefined'

export const auth = writable({
    token: isBrowser ? localStorage.getItem('token') : null,
    user: null,
    loading: false
});

if (isBrowser) {
    auth.subscribe((val) => {
        if (val.token) localStorage.setItem('token', val.token);
        else localStorage.removeItem('token');
    });
}
