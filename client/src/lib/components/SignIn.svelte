<script lang="ts">
	import { Input, Label, Button, Alert } from 'flowbite-svelte';
	import { auth } from '$lib/stores/auth';
	import { signIn } from '$lib/services/userAuthenticationService';

	let email = '';
	let password = '';
	let error: string | null = null;
	let loading = false;

	async function handleSignIn(e: Event) {
		e.preventDefault();
		error = null;
		loading = true;

		try {
			const { data } = await signIn(email, password);
			auth.signIn(data.accessToken);
		} catch (err) {
			error = err instanceof Error ? err.message : 'Unknown error occurred';
			auth.signOut();
		} finally {
			loading = false;
		}
	}
</script>

<div class="w-full max-w-md rounded-xl border border-[#e3e2df] bg-white p-8">
	<h2 class="mb-8 text-center text-2xl font-semibold tracking-tight text-[#22223b]">
		Sign in to your account
	</h2>
	<form class="space-y-6" on:submit={handleSignIn}>
		<div>
			<Label for="email" class="mb-1 block text-[15px] font-medium text-[#444]">Email</Label>
			<Input
				id="email"
				type="email"
				bind:value={email}
				disabled={loading}
				placeholder="you@example.com"
				required
				class="w-full rounded-md border bg-[#fafaf9] px-3 py-2"
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
				class="w-full rounded-md border bg-[#fafaf9] px-3 py-2"
			/>
		</div>
		<div class="flex justify-end">
			<Button type="submit" disabled={loading}>Sign In</Button>
		</div>
	</form>
	{#if error}
		<Alert color="red" class="mt-4">
			{error}
		</Alert>
	{/if}
</div>
