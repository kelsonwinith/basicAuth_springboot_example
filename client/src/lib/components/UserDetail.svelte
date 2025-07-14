<script lang="ts">
	import { auth } from '$lib/stores/auth';
	import { Button } from 'flowbite-svelte';
	import { jwtDecode } from 'jwt-decode';

	let decoded: any = null;

	$: if ($auth.token) {
		try {
			decoded = jwtDecode($auth.token);
		} catch (err) {
			console.error('Invalid token', err);
			decoded = null;
		}
	}

	function handleSignOut() {
		auth
			.signOut()
			.then(() => {
				decoded = null; // Clear decoded token on sign out
			})
			.catch((err) => {
				console.error('Sign out failed', err);
			});
	}
</script>

{#if decoded}
	<div class="w-full max-w-md rounded-xl border border-[#e3e2df] bg-white p-8 text-left">
		<h2 class="mb-4 text-2xl font-semibold text-[#22223b]">User Details</h2>
		<div class="flex flex-col gap-2 pb-4">
			<h3 class="text-lg font-medium text-[#4a4e69]">JWT token</h3>
			<p class="whitespace-pre-wrap break-words font-mono text-sm text-gray-800">
				{$auth.token}
			</p>
		</div>
		<div class="flex flex-col gap-2 pb-4">
			<h3 class="text-lg font-medium text-[#4a4e69]">Decoded JWT</h3>
			<p class="whitespace-pre-wrap break-words font-mono text-sm text-gray-800">
				{JSON.stringify(decoded, null, 2)}
			</p>
		</div>
		<div class="flex flex-col gap-2 pb-4">
			<h3 class="text-lg font-medium text-[#4a4e69]">Readable JWT</h3>
			<p class="whitespace-pre-wrap break-words font-mono text-sm text-gray-800">
				Subject: {decoded.sub}
			</p>
			<p class="whitespace-pre-wrap break-words font-mono text-sm text-gray-800">
				Issued at: {new Date(decoded.iat * 1000).toLocaleString()}
			</p>
			<p class="whitespace-pre-wrap break-words font-mono text-sm text-gray-800">
				Expired at: {new Date(decoded.exp * 1000).toLocaleString()}
			</p>
		</div>

		<div class="mt-4 flex w-full justify-end">
			<form on:submit={async () => await auth.signOut()}>
				<Button type="submit">Sign out</Button>
			</form>
		</div>
	</div>
{:else}
	<p>No user info available.</p>
{/if}
