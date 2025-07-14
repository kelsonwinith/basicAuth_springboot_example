<script lang="ts">
	import { signUp } from '$lib/services/userAuthenticationService';
	import { auth } from '$lib/stores/auth';
	import { Alert, Button, Input, Label } from 'flowbite-svelte';

	let email = '';
	let password = '';
	let confirmPassword = '';
	let error: string | null = null;
	let loading = false;

	async function handleSignUp(e: Event) {
		e.preventDefault();
		error = null;
		loading = true;

		try {
			if (password !== confirmPassword) {
				error = 'Passwords do not match';
				return;
			}

			const { data } = await signUp(email, password);
			auth.signIn(data.accessToken);
		} catch (err) {
			error = err instanceof Error ? err.message : 'Unknown error occurred';
			auth.signOut();
		} finally {
			loading = false;
		}
	}
</script>

<div class="w-full max-w-md rounded-xl border border-[#e3e2df] bg-white p-8 shadow-none">
	<h2 class="mb-8 text-center text-2xl font-semibold tracking-tight text-[#22223b]">
		Sign up for an account
	</h2>
	<form class="space-y-6" on:submit={handleSignUp}>
		<div>
			<Label for="email" class="mb-1 block text-[15px] font-medium text-[#444]">Email</Label>
			<Input
				id="email"
				type="email"
				bind:value={email}
				disabled={loading}
				placeholder="you@example.com"
				required
				class="w-full rounded-md border border-[#e3e2df] bg-[#fafaf9] px-3 py-2 text-[15px] shadow-none focus:border-[#a3a3a3] focus:ring-0"
			/>
		</div>
		<div>
			<Label for="password" class="mb-1 block text-[15px] font-medium text-[#444]">Password</Label>
			<Input
				id="password"
				type="password"
				bind:value={password}
				disabled={loading}
				placeholder="Password"
				required
				class="w-full rounded-md border border-[#e3e2df] bg-[#fafaf9] px-3 py-2 text-[15px] shadow-none focus:border-[#a3a3a3] focus:ring-0"
			/>
		</div>
		<div>
			<Label for="confirmPassword" class="mb-1 block text-[15px] font-medium text-[#444]"
				>Confirm Password</Label
			>
			<Input
				id="confirmPassword"
				type="password"
				bind:value={confirmPassword}
				disabled={loading}
				placeholder="Confirm Password"
				required
				class="w-full rounded-md border border-[#e3e2df] bg-[#fafaf9] px-3 py-2 text-[15px] shadow-none focus:border-[#a3a3a3] focus:ring-0"
			/>
		</div>
		<div class="flex justify-end">
			<Button type="submit" disabled={loading}>Sign Up</Button>
		</div>
		{#if error}
			<Alert color="red" class="mt-4">
				{error}
			</Alert>
		{/if}
	</form>
</div>
