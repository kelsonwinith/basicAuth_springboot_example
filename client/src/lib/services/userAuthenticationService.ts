import type { ApiResponse } from "$lib/types/ApiResponse";
import { auth } from "$lib/stores/auth";
import { get } from 'svelte/store';

type UserAuthenticationResponse = {
    accessToken: string;
}

type response = ApiResponse<UserAuthenticationResponse>;

export async function signIn(email: string, password: string): Promise<response> {
    const res = await fetch('http://localhost:8001/api/v1/auth/sign-in', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password }),
        credentials: 'include'
    });

    if (!res.ok) {
        const error = await res.json();
        throw new Error(error.message || 'Login failed');
    }

    return await res.json();
}

export async function signUp(email: string, password: string): Promise<response> {
    const res = await fetch('http://localhost:8001/api/v1/auth/sign-up', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password }),
        credentials: 'include'
    });

    if (!res.ok) {
        const error = await res.json();
        throw new Error(error.message || 'Sign up failed');
    }

    return await res.json();
}

export async function signOut(): Promise<ApiResponse<null>> {
    const { token } = get(auth);

    if (!token) {
        throw new Error('User is not authenticated');
    }

    const res = await fetch('http://localhost:8001/api/v1/auth/sign-out', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        credentials: 'include'
    });

    if (!res.ok) {
        const error = await res.json();
        throw new Error(error.message || 'Sign out failed');
    }

    return await res.json();
}
